package com.example.cameraapplication


import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cameraapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeFragment : Fragment() {

  lateinit var binding: FragmentHomeBinding
  private var imageCapture: ImageCapture? = null
  private lateinit var cameraExecutor: ExecutorService

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    if (allPermissionsGranted()) {
      startCamera()
    } else {
      ActivityCompat.requestPermissions(
        requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
      )
    }

    // Set up the listeners for take photo and video capture buttons
    binding.imageCaptureButton.setOnClickListener { takePhotoAsBitMapToPreview() }
    cameraExecutor = Executors.newSingleThreadExecutor()
  }

  // private fun takePhotoAndSaveImage() {
  //   // Get a stable reference of the modifiable image capture use case
  //   val imageCapture = imageCapture ?: return
  //
  //   // Create time stamped name and MediaStore entry.
  //   val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
  //   val contentValues = ContentValues().apply {
  //     put(MediaStore.MediaColumns.DISPLAY_NAME, name)
  //     put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
  //     if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
  //       put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
  //     }
  //   }
  //
  //   // Create output options object which contains file + metadata
  //   val outputOptions = ImageCapture.OutputFileOptions.Builder(
  //    requireActivity().contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
  //   ).build()
  //
  //   // Set up image capture listener, which is triggered after photo has
  //   // been taken
  //   imageCapture.takePicture(outputOptions,
  //     ContextCompat.getMainExecutor(requireActivity()),
  //     object : ImageCapture.OnImageSavedCallback {
  //       override fun onError(exc: ImageCaptureException) {
  //         Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
  //       }
  //
  //       override fun onImageSaved(output: ImageCapture.OutputFileResults) {
  //         val msg = "Photo capture succeeded: ${output.savedUri}"
  //         Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
  //         Log.d(TAG, msg)
  //       }
  //     })
  // }

  private fun takePhotoAsBitMapToPreview() {
    val imageCapture = imageCapture ?: return

    imageCapture.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
      override fun onCaptureSuccess(image: ImageProxy) {
        //get bitmap from image
        val bitmap = imageProxyToBitmap(image)//.rotate(270f)

        runBlocking(Dispatchers.Main) {
          binding.fl.visibility = View.VISIBLE
          binding.imgPreview.setImageBitmap(bitmap)
        }
        super.onCaptureSuccess(image)
      }

      override fun onError(exception: ImageCaptureException) {
        super.onError(exception)
      }
    })
  }

  private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
    val planeProxy = image.planes[0]
    val buffer: ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
  }

  private fun startCamera() {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

    cameraProviderFuture.addListener({
      // Used to bind the lifecycle of cameras to the lifecycle owner
      val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

      // Preview
      val preview = Preview.Builder().build().also {
        it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
      }

      imageCapture = ImageCapture.Builder().build()

      // Select back camera as a default
      val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

      try {
        // Unbind use cases before rebinding
        cameraProvider.unbindAll()
        // Bind use cases to camera
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
      } catch (exc: Exception) {
        Log.e(TAG, "Use case binding failed", exc)
      }

    }, ContextCompat.getMainExecutor(requireActivity()))
  }

  private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
    ContextCompat.checkSelfPermission(
      requireActivity(), it
    ) == PackageManager.PERMISSION_GRANTED
  }

  override fun onDestroy() {
    super.onDestroy()
    cameraExecutor.shutdown()
  }

  companion object {
    private const val TAG = "CameraXApp"
    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = mutableListOf(
      Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
    ).apply {
      if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
      }
    }.toTypedArray()
  }

  override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<String>, grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == REQUEST_CODE_PERMISSIONS) {
      if (allPermissionsGranted()) {
        startCamera()
      } else {
        Toast.makeText(
          requireActivity(), "Permissions not granted by the user.", Toast.LENGTH_SHORT
        ).show()
        requireActivity().finish()
      }
    }
  }

}
