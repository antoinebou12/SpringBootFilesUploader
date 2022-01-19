package com.api.demo.service

import com.api.demo.model.BasicModel
import com.api.demo.repository.BasicDAO
import org.springframework.stereotype.Service

@Service
class BasicService(private val basicDAO: BasicDAO) {

//    private val log = LoggerFactory.getLogger(BasicService::class.java)

    fun getBasicRequest(basic: BasicModel): BasicModel? {
        val basicReturn = basicDAO.getBasic(basic.id);
        return basicReturn;
    }

    fun getBasicRequest(id: String): BasicModel? {
        val basicReturn = basicDAO.getBasic(id);
        return basicReturn
    }

    fun putNewBasic(requestId: String, username: String, number: Number, message: String): BasicModel {
        basicDAO.getBasic(requestId)?.let {
            return it;
        }
        return basicDAO.saveBasic(BasicModel(requestId, username, number, message))
    }

    fun modifyBasic(basicModel: BasicModel): BasicModel? {
        return basicDAO.findAndModifiedBasic(basicModel)
    }

    fun modifyPartialBasic(id: String, key: String, value: Any): BasicModel? {
        return basicDAO.findAndModifiedPartialBasic(id, key, value)
    }

    fun removeBasicRequest(id: String): BasicModel? {
        return basicDAO.removeBasic(id)
    }


}