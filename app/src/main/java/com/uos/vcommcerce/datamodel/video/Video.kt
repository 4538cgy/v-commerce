package com.uos.vcommcerce.datamodel.video

import android.net.Uri

data class Video(val uri: Uri,
                 val name: String,
                 val duration: Int,
                 val size: Int
)