package com.uos.vcommcerce.datamodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.uos.vcommcerce.R

//리뷰작성에서 보일 사진및 동영상 아이템
class ReviewUploadItemDTO(var imageUri : Uri?)