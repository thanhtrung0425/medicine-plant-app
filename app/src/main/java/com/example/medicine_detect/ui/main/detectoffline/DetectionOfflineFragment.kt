package com.example.medicine_detect.ui.main.detectoffline

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.medicine_detect.base.BaseFragment
import com.example.medicine_detect.comon.Constants.*
import com.example.medicine_detect.comon.ResultLabel.*
import com.example.medicine_detect.databinding.FragmentDetectOfflineBinding
import com.example.medicine_detect.ml.MedicinePlantsModel
import com.example.medicine_detect.utils.ImageRotationUtil
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import java.io.File
import java.io.IOException
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class DetectionOfflineFragment : BaseFragment() {

    private val binding by lazy { FragmentDetectOfflineBinding.inflate(layoutInflater) }
    private var mCurrentPhotoPath = ""

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            context?.apply {
                try {
                    val imageUri = result.data!!.data
                    val inputStream = contentResolver.openInputStream(imageUri!!)
                    var imgBitmap = BitmapFactory.decodeStream(inputStream)

                    binding.imageDetect.setImageBitmap(imgBitmap)

                    //detect object
                    imgBitmap = Bitmap.createScaledBitmap(imgBitmap, IMAGE_SIZE, IMAGE_SIZE, false)
                    classifyImage(imgBitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            context?.apply {
                try {
                    val imageUri = Uri.fromFile(File(mCurrentPhotoPath))
                    val inputStream = contentResolver.openInputStream(imageUri)
                    var imgBitmap = BitmapFactory.decodeStream(inputStream)

                    imgBitmap = ImageRotationUtil.rotateBitmap(imgBitmap, 90f)
                    binding.imageDetect.setImageBitmap(imgBitmap)

                    //detect object
                    imgBitmap = Bitmap.createScaledBitmap(imgBitmap, IMAGE_SIZE, IMAGE_SIZE, false)
                    classifyImage(imgBitmap)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        private const val IMAGE_SIZE = 224
        fun newInstance() = DetectionOfflineFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onListener()
    }

    private fun onListener() {
        binding.btnPickGallery.setOnClickListener { pickImageFromGallery() }

        binding.btnPickCamera.setOnClickListener { pickImageFromCamera() }
    }

    private fun pickImageFromCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(context?.packageManager!!) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                //No-op
            }

            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    baseActivity,
                    "com.example.medicine_detect.provider",
                    photoFile
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                launcherCamera.launch(
                    Intent.createChooser(takePictureIntent, "Select source")
                )
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcherGallery.launch(intent)
    }

    private fun classifyImage(image: Bitmap) {
        try {
            val model = MedicinePlantsModel.newInstance(requireContext())

//            val inputFeature0 = TensorBuffer.createFixedSize(
//                intArrayOf(1, IMAGE_SIZE, IMAGE_SIZE, 3),
//                DataType.UINT8
//            )
//            val byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3)
//            byteBuffer.order(ByteOrder.nativeOrder())
//
//            val intValues = IntArray(IMAGE_SIZE * IMAGE_SIZE)
//            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
//
//            var pixel = 0
//            for (i in 0 until IMAGE_SIZE) {
//                for (j in 0 until IMAGE_SIZE) {
//                    val value = intValues[pixel++] //RGB
//                    byteBuffer.putFloat((value shr 16 and 0xFF) * (1f / 1))
//                    byteBuffer.putFloat((value shr 8 and 0xFF) * (1f / 1))
//                    byteBuffer.putFloat((value and 0xFF) * (1f / 1))
//                }
//            }
//
//            inputFeature0.loadBuffer(byteBuffer)
//            val outputsss = TensorImage.


            val img = Bitmap.createScaledBitmap(image, IMAGE_SIZE, IMAGE_SIZE, true)
            val tensorImage = TensorImage.fromBitmap(img)

            val outputs = model.process(tensorImage)
            val listCategory = outputs.probabilityAsCategoryList
            listCategory.sortByDescending { it.score }
            printListResult(listCategory)

            model.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun roundOffDecimal(number: Float): String {
        val df = DecimalFormat("#.#####")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toString()
    }

    private fun printListResult(listResult: MutableList<Category>) {
        val stringBuilder = StringBuilder()
        for (item in listResult) {
            stringBuilder.append(getLabel(item.label) + " : " + roundOffDecimal(item.score))
            stringBuilder.append("\n----------------------\n")
        }
        binding.txtResult.text = stringBuilder.toString()
    }

    private fun getLabel(string: String): String {
        return when (string) {
            KEY_BAC_HA -> BAC_HA
            KEY_CU_GUNG -> CU_GUNG
            KEY_DAU_TAM -> DAU_TAM
            KEY_DINH_LANG -> DINH_LANG
            else -> string
        }
    }

}
