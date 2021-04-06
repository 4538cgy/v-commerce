package com.uos.vcommcerce.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.uos.vcommcerce.datamodel.user.UserDTO
import com.uos.vcommcerce.datamodel.user.UserListDTO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow


class FirebaseUserRepository {

    /*
    *
    *


    How to get SnapShot in ViewModel
    * 0. declare repository
    * 1. access 'viewModelScope'
    * 2. launch Dispatchers.IO
    * 3. access repository
    * 4. call repository method
    * 5. collect data

    * ex )
    viewModelScope.launch(Dispatchers.IO) {
        repository.Observe().collect {
            println("data print in viewmodel = " + it.toString())
        }
    }


    *
    *
    */

    private val db = FirebaseFirestore.getInstance()

    //모든 유저의 Field 값 가져오기
    fun getUserDataAll() = callbackFlow<ArrayList<UserDTO>> {
        val databaseReference = db.collection("user")
        val eventListener = databaseReference.addSnapshotListener { value, error ->
            var userData: ArrayList<UserDTO> = arrayListOf()

            if (value!!.isEmpty) return@addSnapshotListener
            value.documents.forEach {
                userData.add(it.toObject(UserDTO::class.java)!!)
            }
            this@callbackFlow.sendBlocking(userData)

        }

        awaitClose { eventListener.remove() }
    }

    //특정 유저 data 값 한번 가져오기
    fun getUserData(uid: String) = callbackFlow<UserDTO> {

        val databaseReference = db.collection("user").document(uid)
        val eventListener = databaseReference.get().addOnCompleteListener {
            var userData = it.result?.toObject(UserDTO::class.java)
            if (it.isSuccessful) this@callbackFlow.sendBlocking(userData!!)
        }.addOnFailureListener {
            println("----------------------------------------")
            println("getUserData Task Fail : ${it.toString()}")
            println("----------------------------------------")
            this@FirebaseUserRepository
        }

    }

    //특정 유저 전체 Data 일괄 Update
    fun updateUserData(uid: String, data: UserDTO) = callbackFlow<Boolean> {
        val transactionReference = db.collection("user").document(uid)
        val runTransaction = db.runTransaction { transaction ->
            var userData = transaction.get(transactionReference).toObject(UserDTO::class.java)

            transaction.set(transactionReference, userData!!)
            this@callbackFlow.sendBlocking(true)
        }.addOnFailureListener {
            println("--------------------------------------------------")
            println("updateUserData Transaction Fail : ${it.toString()}")
            println("--------------------------------------------------")
            this@callbackFlow.sendBlocking(false)
        }
    }

    //특정 유저 데이터 제거
    fun deleteUserData(uid: String) = callbackFlow<Boolean> {
        val databaseReference = db.collection("user").document(uid)
        val eventListener = databaseReference.delete()
            .addOnSuccessListener { this@callbackFlow.sendBlocking(true) }
            .addOnFailureListener {
                println("---------------------------------------------")
                println("deleteUserData Delete Fail : ${it.toString()}")
                println("---------------------------------------------")
                this@callbackFlow.sendBlocking(false)
            }
    }

    //유저 데이터 추가
    fun addUserData(userData: UserDTO) = callbackFlow<Boolean> {
        val databaseReference = db.collection("user").document()
        val eventListener = databaseReference.set(userData)
            .addOnSuccessListener { this@callbackFlow.sendBlocking(true) }
            .addOnFailureListener {
                println("---------------------------------------")
                println("addUserData Add Fail : ${it.toString()}")
                println("---------------------------------------")
                this@callbackFlow.sendBlocking(false)
            }
    }

    //유저 닉네임 리스트에 추가
    fun updateUserList(name: String, type: Int) = callbackFlow<Boolean> {


        val transactionReference = db.collection("userList").document("userData")
        val runTransaction = db.runTransaction { transaction ->
            var list = transaction.get(transactionReference).toObject(UserListDTO::class.java)

            when (type) {
                //회원 탈퇴시엔 삭제
                1 -> {
                    list?.list?.remove(name)
                }
                //회원 가입시엔 추가
                2 -> {
                    list?.list?.put(name, true)
                }
            }

            transaction.set(transactionReference, list!!)
            this@callbackFlow.sendBlocking(true)
        }.addOnFailureListener {
            println("---------------------------------------------")
            println("updateUserList update Fail : ${it.toString()}")
            println("---------------------------------------------")
            this@callbackFlow.sendBlocking(false)
        }
    }

}






