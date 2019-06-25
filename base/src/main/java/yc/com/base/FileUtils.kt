package yc.com.base

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.text.TextUtils
import android.util.Log

import com.alibaba.fastjson.util.IOUtils

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.math.BigInteger
import java.nio.channels.FileChannel
import java.security.MessageDigest
import java.text.DecimalFormat
import java.util.ArrayList

/**
 * @author yuyh.
 * @date 16/4/9.
 */
class FileUtils {

    /**
     * 递归获取所有文件
     *
     * @param root
     * @param ext  指定扩展名
     */
    @Synchronized
    private fun getAllFiles(root: File, ext: String) {
        val list = ArrayList<File>()
        val files = root.listFiles()
        if (files != null) {
            for (f in files) {
                if (f.isDirectory) {
                    getAllFiles(f, ext)
                } else {
                    if (f.name.endsWith(ext) && f.length() > 50)
                        list.add(f)
                }
            }
        }
    }

    companion object {
        fun getPathOPF(unzipDir: String): String? {
            var mPathOPF = ""
            try {
                val br = BufferedReader(InputStreamReader(FileInputStream("$unzipDir/META-INF/container.xml"), "UTF-8"))
                var line: String? = br.readLine()
                while (line != null) {


                    if (line.contains("full-path")) {
                        val start = line.indexOf("full-path")
                        val start2 = line.indexOf('\"', start)
                        val stop2 = line.indexOf('\"', start2 + 1)
                        if (start2 > -1 && stop2 > start2) {
                            mPathOPF = line.substring(start2 + 1, stop2).trim { it <= ' ' }
                            break
                        }
                    }
                    line = br.readLine()
                }
                br.close()

                if (!mPathOPF.contains("/")) {
                    return null
                }

                val last = mPathOPF.lastIndexOf('/')
                if (last > -1) {
                    mPathOPF = mPathOPF.substring(0, last)
                }

                return mPathOPF
            } catch (e: NullPointerException) {

            } catch (e: IOException) {
            }

            return mPathOPF
        }

        fun checkOPFInRootDirectory(unzipDir: String): Boolean {
            var mPathOPF = ""
            var status = false
            try {
                val br = BufferedReader(InputStreamReader(FileInputStream("$unzipDir/META-INF/container.xml"), "UTF-8"))
                var line: String? = br.readLine()
                while (line != null) {

                    if (line.contains("full-path")) {
                        val start = line.indexOf("full-path")
                        val start2 = line.indexOf('\"', start)
                        val stop2 = line.indexOf('\"', start2 + 1)
                        if (start2 > -1 && stop2 > start2) {
                            mPathOPF = line.substring(start2 + 1, stop2).trim { it <= ' ' }
                            break
                        }
                    }
                    line = br.readLine()
                }
                br.close()

                status = !mPathOPF.contains("/")
            } catch (e: NullPointerException) {

            } catch (e: IOException) {
            }

            return status
        }


        /**
         * 创建根缓存目录
         *
         * @return
         */
        fun createRootPath(context: Context): String {
            var cacheRootPath = ""
            //SD卡已挂载，使用SD卡缓存目录，这个缓存补录数据不会随着应用的卸载而清除
            if (isSdCardAvailable) {
                // /sdcard/Android/data/<application package>/cache
                if (null != context.externalCacheDir) {
                    cacheRootPath = context.externalCacheDir!!.path//SD卡内部临时缓存目录
                }
                //内部缓存目录，会随着应用的卸载而清除
            } else {
                // /data/data/<application package>/cache
                if (null != context.cacheDir) {
                    cacheRootPath = context.cacheDir.path//应用内部临时缓存目录
                } else {
                    val cacheDirectory = getCacheDirectory(context, null)
                    if (null != cacheDirectory) {
                        cacheRootPath = cacheDirectory.absolutePath
                    }
                }
            }
            return cacheRootPath
        }

        /**
         * 获取临时文件缓存目录
         *
         * @return
         */
        fun getFileDir(context: Context): String? {
            var cacheRootPath: String? = null
            if (null != context.filesDir) {
                cacheRootPath = context.filesDir.path
            } else if (isSdCardAvailable) {
                if (null != context.externalCacheDir) {
                    cacheRootPath = context.externalCacheDir!!.path//SD卡内部临时缓存目录
                }
            } else if (null != context.cacheDir) {
                cacheRootPath = context.cacheDir.path
            } else {
                val cacheDirectory = getCacheDirectory(context, null)
                if (null != cacheDirectory) {
                    cacheRootPath = cacheDirectory.absolutePath
                }
            }
            return cacheRootPath
        }

        /**
         * 获取临时数据缓存目录
         *
         * @param context
         * @return
         */
        fun getCacheDir(context: Context): String? {
            var cacheRootPath: String? = null
            if (null != context.cacheDir) {
                cacheRootPath = context.cacheDir.path
            } else if (null != context.filesDir) {
                cacheRootPath = context.filesDir.path
            } else if (isSdCardAvailable) {
                if (null != context.externalCacheDir) {
                    cacheRootPath = context.externalCacheDir!!.path//SD卡内部临时缓存目录
                }
            } else {
                val cacheDirectory = getCacheDirectory(context, null)
                if (null != cacheDirectory) {
                    cacheRootPath = cacheDirectory.absolutePath
                }
            }
            return cacheRootPath
        }


        val isSdCardAvailable: Boolean
            get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

        /**
         * 获取应用专属缓存目录
         * android 4.4及以上系统不需要申请SD卡读写权限
         * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
         *
         * @param context 上下文
         * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
         * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
         */
        fun getCacheDirectory(context: Context, type: String?): File? {
            var appCacheDir = getExternalCacheDirectory(context, type)
            if (appCacheDir == null) {
                appCacheDir = getInternalCacheDirectory(context, type)
            }

            if (appCacheDir == null) {
                Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is mobile phone unknown exception !")
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e("getCacheDirectory", "getCacheDirectory fail ,the reason is make directory fail !")
                }
            }
            return appCacheDir
        }

        /**
         * 获取SD卡缓存目录
         *
         * @param context 上下文
         * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
         * 否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
         * [Environment.DIRECTORY_MUSIC],
         * [Environment.DIRECTORY_PODCASTS],
         * [Environment.DIRECTORY_RINGTONES],
         * [Environment.DIRECTORY_ALARMS],
         * [Environment.DIRECTORY_NOTIFICATIONS],
         * [Environment.DIRECTORY_PICTURES], or
         * [Environment.DIRECTORY_MOVIES].or 自定义文件夹名称
         * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
         */
        fun getExternalCacheDirectory(context: Context, type: String?): File? {
            var appCacheDir: File? = null
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                appCacheDir = if (TextUtils.isEmpty(type)) {
                    context.externalCacheDir
                } else {
                    context.getExternalFilesDir(type)
                }

                if (appCacheDir == null) {// 有些手机需要通过自定义目录
                    appCacheDir = File(Environment.getExternalStorageDirectory(), "Android/data/" + context.packageName + "/cache/" + type)
                }

                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is make directory fail !")
                }
            } else {
                Log.e("getExternalDirectory", "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !")
            }
            return appCacheDir
        }

        /**
         * 获取内存缓存目录
         *
         * @param type 子目录，可以为空，为空直接返回一级目录
         * @return 缓存目录文件夹 或 null（创建目录文件失败）
         * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
         */
        fun getInternalCacheDirectory(context: Context, type: String?): File {
            var appCacheDir: File? = null
            if (TextUtils.isEmpty(type)) {
                appCacheDir = context.cacheDir// /data/data/app_package_name/cache
            } else {
                appCacheDir = File(context.filesDir, type!!)// /data/data/app_package_name/files/type
            }

            if (!appCacheDir!!.exists() && !appCacheDir.mkdirs()) {
                Log.e("getInternalDirectory", "getInternalDirectory fail ,the reason is make directory fail !")
            }
            return appCacheDir
        }


        /**
         * 递归创建文件夹
         *
         * @param dirPath
         * @return 创建失败返回""
         */
        fun createDir(dirPath: String): String {
            try {
                val file = File(dirPath)

                if (file.parentFile.exists()) {

                    file.mkdir()
                    return file.absolutePath
                } else {
                    createDir(file.parentFile.absolutePath)

                    file.mkdir()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dirPath
        }

        /**
         * 递归创建文件夹
         *
         * @param file
         * @return 创建失败返回""
         */
        fun createFile(file: File): String {
            try {
                if (file.parentFile.exists()) {

                    file.createNewFile()
                    return file.absolutePath
                } else {
                    createDir(file.parentFile.absolutePath)
                    file.createNewFile()

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        /**
         * 将内容写入文件
         *
         * @param filePath eg:/mnt/sdcard/demo.txt
         * @param content  内容
         * @param isAppend 是否追加
         */
        fun writeFile(filePath: String, content: String, isAppend: Boolean) {

            try {
                val fout = FileOutputStream(filePath, isAppend)
                val bytes = content.toByteArray()
                fout.write(bytes)
                fout.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun writeFile(filePathAndName: String, fileContent: String) {
            try {
                val outstream = FileOutputStream(filePathAndName)
                val out = OutputStreamWriter(outstream)
                out.write(fileContent)
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        /**
         * 获取Raw下的文件内容
         *
         * @param context
         * @param resId
         * @return 文件内容
         */
        fun getFileFromRaw(context: Context?, resId: Int): String? {
            if (context == null) {
                return null
            }

            val s = StringBuilder()
            try {
                val `in` = InputStreamReader(context.resources.openRawResource(resId))
                val br = BufferedReader(`in`)
                var line: String? = br.readLine()
                while (line != null) {
                    line = br.readLine()
                    s.append(line)
                }
                return s.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

        }

        fun getBytesFromFile(f: File?): ByteArray? {
            if (f == null) {
                return null
            }
            try {
                val stream = FileInputStream(f)
                val out = ByteArrayOutputStream(1000)
                val b = ByteArray(1000)
                var n = 0
                while (n != -1) {
                    n = stream.read(b)
                    out.write(b, 0, n)
                }
                stream.close()
                out.close()
                return out.toByteArray()
            } catch (e: IOException) {
            }

            return null
        }

        /**
         * 文件拷贝
         *
         * @param src  源文件
         * @param desc 目的文件
         */
        fun fileChannelCopy(src: File, desc: File) {
            //createFile(src);
            createFile(desc)
            var fi: FileInputStream? = null
            var fo: FileOutputStream? = null
            try {
                fi = FileInputStream(src)
                fo = FileOutputStream(desc)
                val `in` = fi.channel//得到对应的文件通道
                val out = fo.channel//得到对应的文件通道
                `in`.transferTo(0, `in`.size(), out)//连接两个通道，并且从in通道读取，然后写入out通道
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    fo?.close()
                    fi?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        /**
         * 转换文件大小
         *
         * @param fileLen 单位B
         * @return
         */
        fun formatFileSizeToString(fileLen: Long): String {
            val df = DecimalFormat("0.00")
            var fileSizeString = ""
            fileSizeString = if (fileLen < 1024) {
                df.format(fileLen.toDouble()) + "B"
            } else if (fileLen < 1048576) {
                df.format(fileLen.toDouble() / 1024) + "K"
            } else if (fileLen < 1073741824) {
                df.format(fileLen.toDouble() / 1048576) + "M"
            } else {
                df.format(fileLen.toDouble() / 1073741824) + "G"
            }
            return fileSizeString
        }

        fun getFileSize(file: File): String? {
            return if (file.exists() && file.isFile) {
                formatFileSizeToString(file.length())
            } else null
        }

        /**
         * 删除指定文件
         *
         * @param file
         * @return
         * @throws IOException
         */
        fun deleteFile(file: File): Boolean {
            try {
                return deleteFileOrDirectory(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return false
        }

        /**
         * 删除指定文件，如果是文件夹，则递归删除
         *
         * @param file
         * @return
         * @throws IOException
         */
        @Throws(IOException::class)
        fun deleteFileOrDirectory(file: File): Boolean {
            if (!file.exists()) return false
            try {
                if (file.isFile) {
                    return file.delete()
                }
                if (file.isDirectory) {
                    val childFiles = file.listFiles()
                    // 删除空文件夹
                    if (childFiles == null || childFiles.isEmpty()) {
                        return file.delete()
                    }
                    // 递归删除文件夹下的子文件
                    for (i in childFiles.indices) {
                        deleteFileOrDirectory(childFiles[i])
                    }
                    return file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }

        /**
         * 获取文件夹大小
         *
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getFolderSize(dir: String): Long {
            val file = File(dir)
            var size: Long = 0
            try {
                val fileList = file.listFiles()
                for (i in fileList.indices) {
                    // 如果下面还有文件
                    size = if (fileList[i].isDirectory) {
                        size + getFolderSize(fileList[i].absolutePath)
                    } else {
                        size + fileList[i].length()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return size
        }

        /***
         * 获取文件扩展名
         *
         * @param filename 文件名
         * @return
         */
        fun getExtensionName(filename: String?): String? {
            if (filename != null && filename.isNotEmpty()) {
                val dot = filename.lastIndexOf('.')
                if (dot > -1 && dot < filename.length - 1) {
                    return filename.substring(dot + 1)
                }
            }
            return filename
        }

        /**
         * 获取文件内容
         *
         * @param path
         * @return
         */
        fun getFileOutputString(path: String, charset: String): String? {
            try {
                val file = File(path)
                val bufferedReader = BufferedReader(InputStreamReader(FileInputStream(file), charset), 8192)
                val sb = StringBuilder()
                var line: String? = bufferedReader.readLine()
                while (line != null) {
                    line = bufferedReader.readLine()
                    sb.append("\n").append(line)
                }
                bufferedReader.close()
                return sb.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        fun getCharset(fileName: String): String {
            var bis: BufferedInputStream? = null
            var charset = "GBK"
            val first3Bytes = ByteArray(3)
            try {
                var checked = false
                bis = BufferedInputStream(FileInputStream(fileName))
                bis.mark(0)
                var read = bis.read(first3Bytes, 0, 3)
                if (read == -1)
                    return charset
                if (first3Bytes[0] == 0xFF.toByte() && first3Bytes[1] == 0xFE.toByte()) {
                    charset = "UTF-16LE"
                    checked = true
                } else if (first3Bytes[0] == 0xFE.toByte() && first3Bytes[1] == 0xFF.toByte()) {
                    charset = "UTF-16BE"
                    checked = true
                } else if (first3Bytes[0] == 0xEF.toByte()
                        && first3Bytes[1] == 0xBB.toByte()
                        && first3Bytes[2] == 0xBF.toByte()) {
                    charset = "UTF-8"
                    checked = true
                }
                bis.mark(0)
                if (!checked) {
                    while (read != -1) {
                        read = bis.read()
                        if (read >= 0xF0)
                            break
                        if (read in 0x80..0xBF)
                        // 单独出现BF以下的，也算是GBK
                            break
                        if (read in 0xC0..0xDF) {
                            read = bis.read()
                            if (read in 0x80..0xBF)
                            // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                                continue
                            else
                                break
                        } else if (read in 0xE0..0xEF) {// 也有可能出错，但是几率较小
                            read = bis.read()
                            if (read in 0x80..0xBF) {
                                read = bis.read()
                                if (read in 0x80..0xBF) {
                                    charset = "UTF-8"
                                    break
                                } else
                                    break
                            } else
                                break
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (bis != null) {
                    try {
                        bis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

            return charset
        }

        @Throws(IOException::class)
        fun getCharset1(fileName: String): String {
            val bin = BufferedInputStream(FileInputStream(fileName))
            val p = (bin.read() shl 8) + bin.read()

            val code: String
            code = when (p) {
                0xefbb -> "UTF-8"
                0xfffe -> "Unicode"
                0xfeff -> "UTF-16BE"
                else -> "GBK"
            }
            return code
        }

        fun saveWifiTxt(src: String, desc: String) {
            val LINE_END = "\n".toByteArray()
            try {
                val isr = InputStreamReader(FileInputStream(src), getCharset(src))
                val br = BufferedReader(isr)

                val fout = FileOutputStream(desc, true)
                var temp = br.readLine()
                while (temp != null) {
                    temp = br.readLine()
                    val bytes = temp.toByteArray()
                    fout.write(bytes)
                    fout.write(LINE_END)
                }
                br.close()
                fout.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        /**
         * 获取系统缓存路径
         *
         * @param context
         * @return
         */
        fun getDiskCacheDir(context: Context): String? {
            var cachePath: String? = null
            try {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                    if (context.externalCacheDir != null) {
                        cachePath = context.externalCacheDir!!.path
                    } else {
                        context.cacheDir.path
                    }
                } else {
                    cachePath = context.cacheDir.path
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return cachePath
        }

        /**
         * 读取表情配置文件
         *
         * @param context
         * @return
         */
        fun getEmojiFile(context: Context): List<String>? {
            try {
                val list = ArrayList<String>()
                val `in` = context.resources.assets.open("emoji")
                val br = BufferedReader(InputStreamReader(`in`, "UTF-8"))
                val str = br.readLine()
                while (str != null) {
                    list.add(str)
                }

                return list
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        val FILE_EXTENSION_SEPARATOR = "."


        fun readFileToList(filePath: String, charsetName: String): List<String>? {
            val file = File(filePath)
            val fileContent = ArrayList<String>()
            if (!file.isFile) {
                return null
            }

            var reader: BufferedReader? = null
            try {
                val `is` = InputStreamReader(FileInputStream(file), charsetName)
                reader = BufferedReader(`is`)
                var line = reader.readLine()
                while (line != null) {
                    line = reader.readLine()
                    fileContent.add(line)
                }
                return fileContent
            } catch (e: IOException) {
                throw RuntimeException("IOException occurred. ", e)
            } finally {
                IOUtils.close(reader)
            }
        }


        fun formatSize(size: Long): String? {
            val tmpSize = size.toDouble()
            return if (tmpSize / 1024 < 1) {
                "" + tmpSize.toInt() + "B"
            } else if (tmpSize / 1024.0 / 1024.0 < 1) {
                "" + (tmpSize / 1024).toInt() + "KB"
            } else if (tmpSize / 1024.0 / 1024.0 / 1024.0 < 1) {
                "" + leaveTwo(tmpSize, (1024 * 1024).toLong()) + "MB"
            } else if (tmpSize / 1024.0 / 1024.0 / 1024.0 / 1024.0 < 1) {
                "" + leaveTwo(tmpSize, (1024 * 1024 * 1024).toLong()) + "GB"
            } else if (tmpSize / 1024.0 / 1024.0 / 1024.0 / 1024.0 / 1024.0 < 1) {
                "" + leaveTwo(tmpSize, (1024 * 1024 * 1024 * 1024).toLong()) + "TB"
            } else {
                null
            }
        }

        private fun leaveTwo(num: Double, base: Long): Double {
            val tmp = (num / base * 100).toInt()
            return 1.0 * tmp / 100
        }

        fun getDirAndFileName(path: String): Array<String>? {
            try {
                val index = path.lastIndexOf("/")
                if (index > 0) {
                    val dir = path.substring(0, index)
                    val fileName = path.substring(index + 1)
                    return arrayOf(dir, fileName)
                }
            } catch (e: Exception) {
                // e.printStackTrace();
            }

            return null
        }


        fun renameFile(sourceFilePath: String?, downPath: String?): Boolean {
            var flag = false
            try {
                if (sourceFilePath != null && downPath != null) {
                    val file = File(sourceFilePath)
                    if (file.exists()) {
                        val downFile = File(downPath)
                        file.renameTo(downFile)
                        flag = true
                    }
                }
            } catch (e: Exception) {
            }

            return flag
        }

        /**
         * 检测文件是否可用
         */
        fun checkFile(f: File?): Boolean {
            return f != null && f.exists() && f.canRead() && (f.isDirectory || f.isFile && f.length() > 0)
        }

        /**
         * 检测文件是否可用
         */
        fun checkFile(path: String): Boolean {
            if (!TextUtils.isEmpty(path)) {
                val f = File(path)
                if (f.exists() && f.canRead() && (f.isDirectory || f.isFile && f.length() > 0))
                    return true
            }
            return false
        }

        /**
         * 保存BitMap到相册中
         *
         * @param bitmap
         * @param outPathFileName
         * @return
         */
        fun saveBitmap(bitmap: Bitmap, outPathFileName: String): String? {
            val f = File(outPathFileName)
            var fOut: FileOutputStream? = null
            try {
                fOut = FileOutputStream(f)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut)
            try {
                fOut.flush()
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

            try {
                fOut.close()
                return f.absolutePath
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

        }


        @Throws(FileNotFoundException::class)
        fun getMd5ByFile(file: File): String? {

            var value: String? = null
            val `in` = FileInputStream(file)
            try {
                val byteBuffer = `in`.channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
                val md5 = MessageDigest.getInstance("MD5")
                md5.update(byteBuffer)
                val bi = BigInteger(1, md5.digest())
                value = bi.toString(16)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return value
        }

        fun getCacheDiskPath(var0: Context?, var1: String): File {
            var var2 = "/mnt/sdcard/Android/data/com.video.newqu/cache"
            if (var0 != null) {
                if ("mounted" == Environment.getExternalStorageState()) {
                    try {
                        var2 = var0.externalCacheDir!!.path
                    } catch (var6: Exception) {
                        Log.e("cache", "[getDiskCacheDir]", var6)

                        try {
                            var2 = var0.cacheDir.path
                        } catch (var5: Exception) {
                            Log.e("cache", "[getDiskCacheDir]", var5)
                        }

                    }

                } else {
                    try {
                        var2 = var0.cacheDir.path
                    } catch (var4: Exception) {
                        Log.e("cache", "[getDiskCacheDir]", var4)
                    }

                }
            }

            return File(var2 + File.separator + var1)
        }


        fun getFolderFiles(context: Context, path: String?): List<String>? {
            if (path == null || path == "") {
                return null
            }
            val stringList = ArrayList<String>()

            val file = File(CacheUtils.makeBaseDir(context, path))
            if (!file.exists()) {
                return null
            }
            if (file.isFile) {
                stringList.add(file.absolutePath)
                return stringList
            }

            if (file.isDirectory) {
                if (file.listFiles() == null || file.listFiles().isEmpty()) {
                    return null
                }
                for (file1 in file.listFiles()) {
                    stringList.add(file1.absolutePath)
                }
            }
            return stringList
        }
    }
}