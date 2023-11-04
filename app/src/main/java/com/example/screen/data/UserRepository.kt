package com.example.screen.data

import android.util.Log
import com.example.screen.data.model.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private var auth: FirebaseAuth = Firebase.auth
    private var db =Firebase.firestore
    suspend fun registerUser(email: String, password: String): ResourceRemote<String?> {
        return try {

            val result=  auth.createUserWithEmailAndPassword(email,password).await()
            result.user?.sendEmailVerification()
            ResourceRemote.Success(data = result.user?.uid)
        } catch (e:FirebaseAuthException){
            Log.e( "Register",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e:FirebaseNetworkException){
            Log.e("RegisterNetwork",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }catch (e: FirebaseException){
            Log.e("FirebaseException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }

    }



    suspend fun loginUser(email: String, password: String): ResourceRemote<String?> {
        return try {
            val result=  auth.signInWithEmailAndPassword(email,password).await()
            result.user?.sendEmailVerification()
            ResourceRemote.Success(data = result.user?.uid)
        } catch (e:FirebaseAuthException){
            Log.e( "Register",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e:FirebaseNetworkException){
            Log.e("RegisterNetwork",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }catch (e: FirebaseException){
            Log.e("FirebaseException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun createUser(user: User): ResourceRemote<String?>{
        return try {
            user.uid?.let { db.collection("users").document(it).set(user).await() }
            ResourceRemote.Success(data = user.uid)
        } catch (e:FirebaseFirestoreException){
            Log.e( "FirebaseFirestoreException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e:FirebaseNetworkException){
            Log.e("FirebaseNetworkException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }catch (e: FirebaseException){
            Log.e("FirebaseException",e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }

    }

}