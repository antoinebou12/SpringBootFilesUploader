package com.api.project.service

import org.apache.sshd.client.SshClient
import org.apache.sshd.client.channel.ClientChannelEvent
import org.apache.sshd.common.channel.Channel
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.concurrent.TimeUnit


@Service
class SSHService {

    fun startSSH(username: String, password: String, host: String, port: Int, command: String): String {
        val cli = command + "\n"
        val defaultTimeoutSeconds = 10L
        val client = SshClient.setUpDefaultClient()
        client.start()
        try {
            client.connect(username, host, port)
                .verify(defaultTimeoutSeconds, TimeUnit.SECONDS)
                .session.use { session ->
                    session.addPasswordIdentity(password)
                    session.auth()
                        .verify(defaultTimeoutSeconds, TimeUnit.SECONDS)
                    ByteArrayOutputStream().use { responseStream ->
                        ByteArrayOutputStream().use { errorResponseStream ->
                            session.createChannel(Channel.CHANNEL_SHELL).use { channel ->
                                channel.setOut(responseStream)
                                channel.setErr(errorResponseStream)
                                return channel.use {
                                    it.open()
                                        .verify(defaultTimeoutSeconds, TimeUnit.SECONDS)
                                    it.invertedIn.use { pipedIn ->
                                        pipedIn.write(cli.toByteArray())
                                        pipedIn.flush()
                                    }
                                    it.waitFor(
                                        EnumSet.of(ClientChannelEvent.CLOSED),
                                        TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds)
                                    )
                                    val errorString = String(errorResponseStream.toByteArray())
                                    if (errorString.isNotEmpty()) {
                                        throw Exception(errorString)
                                    }
                                    String(responseStream.toByteArray())
                                }
                            }
                        }
                    }
                }
        } finally {
            client.stop()
        }
    }
}
