package com.uos.vcommcerce.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.uos.vcommcerce.datamodel.product.ProductDTO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow

class FirebaseContentRepository {

    val db = FirebaseFirestore.getInstance()

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
    //모든 게시글 다 가져오기
    fun getContentAll() = callbackFlow<ArrayList<ProductDTO>> {
        val databaseReference =
            db.collection("content").orderBy("timestamp", Query.Direction.DESCENDING)
        val eventListener = databaseReference.addSnapshotListener { value, error ->
            var contents: ArrayList<ProductDTO> = arrayListOf()

            if (value!!.isEmpty) return@addSnapshotListener
            value.documents.forEach {
                contents.add(it.toObject(ProductDTO::class.java)!!)
            }
            this@callbackFlow.sendBlocking(contents)

        }

        awaitClose { eventListener.remove() }
    }

    //게시글 n개 가져오기 / 시간순 오름차순 정렬
    //return값은 Map에 ArrayList ( index 한개만 존재 )가 id와 Content가 <Key,Value> 형태로 물려있는 형태
    //map의 0번째 인덱스의 0번째 item을 가져오면 list가 나옴 ㅇ.ㅇ
    fun getContentWithIndexCountAndLastVisible(count: Long, lastVisibleDataId: String) =
        callbackFlow<Map<ArrayList<String>, ArrayList<ProductDTO>>> {
            val databaseReference =
                db.collection("content").orderBy("timestamp", Query.Direction.DESCENDING)
                    .startAt(lastVisibleDataId).limit(count)
            val eventListener = databaseReference.addSnapshotListener { value, error ->

                var contents: ArrayList<ProductDTO> = arrayListOf()
                var contentsId: ArrayList<String> = arrayListOf()
                var dataTable: MutableMap<ArrayList<String>, ArrayList<ProductDTO>> = HashMap()


                if (value!!.isEmpty) return@addSnapshotListener
                value.documents.forEach {
                    contents.add(it.toObject(ProductDTO::class.java)!!)
                    contentsId.add(it.id)

                }
                dataTable.put(contentsId, contents)

                this@callbackFlow.sendBlocking(dataTable)
            }
            awaitClose { eventListener.remove() }
        }

    fun deleteContent(contentId: String, uid: String) {

    }

    fun updateContent(contentId: String, uid: String) {

    }

    fun uploadContent(uid: String, contentData : ProductDTO){

    }


}