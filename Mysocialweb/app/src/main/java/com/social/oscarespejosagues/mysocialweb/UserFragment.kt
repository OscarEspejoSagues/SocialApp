package com.social.oscarespejosagues.mysocialweb


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_user.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Check if logged in
        FirebaseAuth.getInstance().currentUser?.let {
            //User
            userField.visibility = View.VISIBLE;
            signupB.visibility = View.GONE;
            loginB.visibility = View.GONE;
            signoutB.visibility = View.VISIBLE;
            // Fill user data
            val db = FirebaseFirestore.getInstance()
                // Get user
            db.collection("users").document(it.uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val userProfile = documentSnapshot.toObject(UserProfile::class.java)
                    userProfile?.let {userProfile ->
                        // Populate fields
                        username.text = userProfile.username
                        useremail.text = userProfile.email
                        userImage.setOnClickListener{
                            takepicture();
                        }
                    }
                }
                .addOnFailureListener {
                    // TODO: failure getitng user
                }
                signoutB.setOnClickListener {

                }


        }?: kotlin.run{
            //No user
            userField.visibility = View.GONE;
            signupB.visibility = View.VISIBLE;
            loginB.visibility = View.VISIBLE;
            signoutB.visibility = View.GONE;
            signupB.setOnClickListener{
                val signUpIntent = Intent(
                    activity,
                    SignUpActivity::class.java
                )
                startActivity(signUpIntent)
                return@setOnClickListener
            }
            loginB.setOnClickListener{
                val logInIntent = Intent(
                    activity,
                    LogInActivity::class.java
                )
                startActivity(logInIntent)
                return@setOnClickListener
            }
        }

    }
    val REQUEST_IMAGE_CAPTURE = 1
    private fun takepicture(){
        activity?.let { activity->
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(activity.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            activity,
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    var mCurrentPhotoPath: String? = null
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        activity?.let { activity ->
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = absolutePath
            }
        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            mCurrentPhotoPath?.let {
              userImage.setImageURI(Uri.fromFile(File(it)));
                val file = File(it);
                val avatarStorageReference = FirebaseStorage.getInstance().getReference("images/users/${file.name}.jpg")
                val uri = Uri.fromFile(file)
                val uploadTask = avatarStorageReference.putFile(uri)
                    uploadTask.addOnSuccessListener {
                        // Get Download Url
                        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    it.printStackTrace()
                                }
                            }
                            return@Continuation avatarStorageReference.downloadUrl
                        }).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Got URL!!
                                val downloadUri = task.result
                                FirebaseAuth.getInstance().currentUser?.uid?.let { uid->
                                    FirebaseFirestore.getInstance().collection("user").document(uid)
                                        .update("avatarUrl",downloadUri.toString())
                                }
                                // TODO: Save to user profile

                            } else {
                                // Handle failures

                            }
                        }

                    }
                uploadTask.addOnFailureListener{
                    it.printStackTrace();
                }
            }
            //val imageBitmap = data?.extras?.get("data") as Bitmap
            //userImage.setImageBitmap(imageBitmap)
        }
    }

}
