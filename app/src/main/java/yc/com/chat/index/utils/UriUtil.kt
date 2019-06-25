package yc.com.chat.index.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.net.Uri.Builder
import android.provider.ContactsContract
import android.provider.MediaStore.Images.Media

import java.io.File

object UriUtil {
    val DATA_SCHEME = "data"
    val HTTPS_SCHEME = "https"
    val HTTP_SCHEME = "http"
    val LOCAL_ASSET_SCHEME = "asset"
    private val LOCAL_CONTACT_IMAGE_PREFIX = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo").path
    val LOCAL_CONTENT_SCHEME = "content"
    val LOCAL_FILE_SCHEME = "file"
    val LOCAL_RESOURCE_SCHEME = "res"
    val QUALIFIED_RESOURCE_SCHEME = "android.resource"

    fun getRealPathFromUri(contentResolver: ContentResolver, srcUri: Uri): String? {
        var str: String? = null
        if (!isLocalContentUri(srcUri)) {
            return if (isLocalFileUri(srcUri)) srcUri.path else null
        } else {
            var cursor: Cursor? = null
            try {
                cursor = contentResolver.query(srcUri, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex("_data")
                    if (columnIndex != -1) {
                        str = cursor.getString(columnIndex)
                    }
                }
                if (cursor == null) {
                    return str
                }
                cursor.close()
                return str
            } catch (th: Throwable) {
                cursor?.close()
            }

        }
        return null
    }

    fun getSchemeOrNull(uri: Uri?): String? {
        return uri?.scheme
    }

    fun getUriForFile(file: File): Uri {
        return Uri.fromFile(file)
    }

    fun getUriForQualifiedResource(packageName: String, resourceId: Int): Uri {
        return Builder().scheme(QUALIFIED_RESOURCE_SCHEME).authority(packageName).path(resourceId.toString()).build()
    }

    fun getUriForResourceId(resourceId: Int): Uri {
        return Builder().scheme(LOCAL_RESOURCE_SCHEME).path(resourceId.toString()).build()
    }

    fun isDataUri(uri: Uri?): Boolean {
        return DATA_SCHEME == getSchemeOrNull(uri)
    }

    fun isLocalAssetUri(uri: Uri?): Boolean {
        return LOCAL_ASSET_SCHEME == getSchemeOrNull(uri)
    }

    fun isLocalCameraUri(uri: Uri): Boolean {
        val uri2 = uri.toString()
        return uri2.startsWith(Media.EXTERNAL_CONTENT_URI.toString()) || uri2.startsWith(Media.INTERNAL_CONTENT_URI.toString())
    }

    fun isLocalContactUri(uri: Uri): Boolean {
        return isLocalContentUri(uri) && "com.android.contacts" == uri.authority && !uri.path.startsWith(LOCAL_CONTACT_IMAGE_PREFIX)
    }

    fun isLocalContentUri(uri: Uri?): Boolean {
        return LOCAL_CONTENT_SCHEME == getSchemeOrNull(uri)
    }

    fun isLocalFileUri(uri: Uri?): Boolean {
        return LOCAL_FILE_SCHEME == getSchemeOrNull(uri)
    }

    fun isLocalResourceUri(uri: Uri?): Boolean {
        return LOCAL_RESOURCE_SCHEME == getSchemeOrNull(uri)
    }

    fun isNetworkUri(uri: Uri?): Boolean {
        val schemeOrNull = getSchemeOrNull(uri)
        return HTTPS_SCHEME == schemeOrNull || HTTP_SCHEME == schemeOrNull
    }

    fun isQualifiedResourceUri(uri: Uri?): Boolean {
        return QUALIFIED_RESOURCE_SCHEME == getSchemeOrNull(uri)
    }

    fun parseUriOrNull(uriAsString: String?): Uri? {
        return if (uriAsString != null) Uri.parse(uriAsString) else null
    }
}