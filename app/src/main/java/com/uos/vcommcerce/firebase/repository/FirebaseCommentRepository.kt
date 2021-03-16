package com.uos.vcommcerce.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.uos.vcommcerce.datamodel.ProductDTO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow

class FirebaseCommentRepository {

    val db = FirebaseFirestore.getInstance()

    //댓글 추가
    fun addComment(contentUid: String,comment: ProductDTO.Review) = callbackFlow<Boolean> {
        val databaseReference = db.collection("content").document(contentUid).collection("comments").document().set(comment)
        val eventListener = databaseReference.addOnCompleteListener {

            this@callbackFlow.sendBlocking(true)

            commentRatingCountRatingUpdate(contentUid, comment.rating!!)
        }.addOnFailureListener {
            this@callbackFlow.sendBlocking(false)
        }
    }
    //댓글 추가되면서 변경되어야할 게시글의 데이터 수정
    fun commentRatingCountRatingUpdate(contentUid: String,rating : Long) = callbackFlow<Boolean> {
        val transactionReference = db.collection("content").document(contentUid)
        val runTransaction = db.runTransaction {
            transaction ->

            var snapshot = transaction.get(transactionReference).toObject(ProductDTO::class.java)
            //Rating 한개 추가
            val newRatingCount = snapshot?.ratingCount!! + 1
            //Rating이 추가됬으므로 새로운 총합 레이팅 평균 계산
            // 1. 원래 총합 리뷰 평균 점수 * 원래 리뷰 갯수
            // 2. 1에 새로운 리뷰 점수 더하기
            // 3. 2에 새로운 리뷰 갯수 나누기
            val newRatingTotal =  ((snapshot.totalRating!!* snapshot.ratingCount!!)+rating)/newRatingCount

            transaction.update(transactionReference,"ratingCount", newRatingCount)
            transaction.update(transactionReference,"totalRating", newRatingTotal)

        }.addOnCompleteListener {
            this@callbackFlow.sendBlocking(true)
        }.addOnFailureListener {
            this@callbackFlow.sendBlocking(false)
        }
    }
}