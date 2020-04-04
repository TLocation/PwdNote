package com.location.pwdnote

import java.io.ByteArrayOutputStream
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 *
 * @author tianxiaolong
 * time：2020/4/4 10:30 PM
 * description：
 */
fun String.toMd5(): String {
    val hash = MessageDigest.getInstance("MD5").digest(toByteArray(charset("UTF-8")))
    val builder = StringBuilder(hash.size * 2)
    for (h in hash) {
        if ((h.toInt() and 0xFF) < 0x10) {
            builder.append("0")
        }
        builder.append(Integer.toHexString((h.toInt() and 0xFF)))
    }
    return builder.toString()
}

//每次最大长度
const val ENCRYPT_MAX_SIZE = 245//每次最大加密长度
const val transformation = "RSA"
const val DECRYPT_MAX_SIZE = 256//每次最大解密长度


fun String.encryRsa() = encryptByPublicKey(this)
fun String.decryRsa() = decryptByPrivateKey(this)




/**
 * 公钥分段加密
 * @param input 原文
 * @param privateKey 公钥
 */
fun encryptByPublicKey(input: String): String {
    val byteArray = input.toByteArray()

    var cipher = Cipher.getInstance(transformation)
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)

    var temp: ByteArray? = null
    var offset = 0//当前偏移位置
    var bos = ByteArrayOutputStream()
    while (byteArray.size - offset > 0) {  //没有加密完成
        if (byteArray.size - offset >= ENCRYPT_MAX_SIZE) {
            temp = cipher.doFinal(byteArray, offset, ENCRYPT_MAX_SIZE)
            offset += ENCRYPT_MAX_SIZE
        } else {
            temp = cipher.doFinal(byteArray, offset, byteArray.size - offset)
            offset = byteArray.size
        }
        bos.write(temp)
    }
    bos.close()
    return encode(bos.toByteArray())
}


/**
 * 私钥分段解密
 * @param input 密文
 * @param privateKey 私钥
 */
fun decryptByPrivateKey(input: String): String {
    val byteArray = decode(input)

    var cipher = Cipher.getInstance(transformation)
    cipher.init(Cipher.DECRYPT_MODE, privateKey)

    var temp: ByteArray? = null
    var offset = 0//当前偏移位置
    var bos = ByteArrayOutputStream()
    while (byteArray.size - offset > 0) {  //没有解密完成
        if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
            temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
            offset += DECRYPT_MAX_SIZE
        } else {
            temp = cipher.doFinal(byteArray, offset, byteArray.size - offset)
            offset = byteArray.size
        }
        bos.write(temp)
    }
    bos.close()
    return String(bos.toByteArray())
}

// val  publicKey = KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(decode(BuildConfig.PUBLIC_KEY)))
// val  privateKey = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(decode(BuildConfig.PRIVATE_KEY)))
val publicKey by lazy {
    KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(decode(BuildConfig.PUBLIC_KEY)))
}
val privateKey by lazy {
    KeyFactory.getInstance("RSA")
        .generatePrivate(PKCS8EncodedKeySpec(decode(BuildConfig.PRIVATE_KEY)))
}


fun encode(data: ByteArray): String {
    val sb = StringBuffer()
    val len = data.size
    var i = 0
    var b1: Int
    var b2: Int
    var b3: Int
    while (i < len) {
        b1 = ((data[i++]).toInt() and 0xff).toInt()
        if (i == len) {
            sb.append(base64EncodeChars[b1.ushr(2)])
            sb.append(base64EncodeChars[b1 and 0x3 shl 4])
            sb.append("==")
            break
        }
        b2 = (data[i++]).toInt() and 0xff
        if (i == len) {
            sb.append(base64EncodeChars[b1.ushr(2)])
            sb.append(base64EncodeChars[b1 and 0x03 shl 4 or (b2 and 0xf0).ushr(4)])
            sb.append(base64EncodeChars[b2 and 0x0f shl 2])
            sb.append("=")
            break
        }
        b3 = (data[i++]).toInt() and 0xff
        sb.append(base64EncodeChars[b1.ushr(2)])
        sb.append(base64EncodeChars[b1 and 0x03 shl 4 or (b2 and 0xf0).ushr(4)])
        sb.append(base64EncodeChars[b2 and 0x0f shl 2 or (b3 and 0xc0).ushr(6)])
        sb.append(base64EncodeChars[b3 and 0x3f])
    }
    return sb.toString()
}

fun decode(str: String): ByteArray {
    val sb = StringBuffer()
    val data = str.toByteArray(charset("US-ASCII"))
    val len = data.size
    var i = 0
    var b1: Int
    var b2: Int
    var b3: Int
    var b4: Int
    while (i < len) {
        /* b1 */
        do {
            b1 = base64DecodeChars[(data[i++]).toInt()].toInt()
        } while (i < len && b1 == -1)
        if (b1 == -1) break
        /* b2 */
        do {
            b2 = base64DecodeChars[(data[i++]).toInt()].toInt()
        } while (i < len && b2 == -1)
        if (b2 == -1) break
        sb.append((b1 shl 2 or (b2 and 0x30).ushr(4)).toChar())
        /* b3 */
        do {
            b3 = data[i++].toInt()
            if (b3 == 61) return sb.toString().toByteArray(charset("ISO-8859-1"))
            b3 = base64DecodeChars[b3].toInt()
        } while (i < len && b3 == -1)
        if (b3 == -1) break
        sb.append((b2 and 0x0f shl 4 or (b3 and 0x3c).ushr(2)).toChar())
        /* b4 */
        do {
            b4 = data[i++].toInt()
            if (b4 == 61) return sb.toString().toByteArray(charset("ISO-8859-1"))
            b4 = base64DecodeChars[b4].toInt()
        } while (i < len && b4 == -1)
        if (b4 == -1) break
        sb.append((b3 and 0x03 shl 6 or b4).toChar())
    }
    return sb.toString().toByteArray(charset("ISO-8859-1"))
}

private val base64DecodeChars = byteArrayOf(
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    62,
    -1,
    -1,
    -1,
    63,
    52,
    53,
    54,
    55,
    56,
    57,
    58,
    59,
    60,
    61,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    0,
    1,
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    10,
    11,
    12,
    13,
    14,
    15,
    16,
    17,
    18,
    19,
    20,
    21,
    22,
    23,
    24,
    25,
    -1,
    -1,
    -1,
    -1,
    -1,
    -1,
    26,
    27,
    28,
    29,
    30,
    31,
    32,
    33,
    34,
    35,
    36,
    37,
    38,
    39,
    40,
    41,
    42,
    43,
    44,
    45,
    46,
    47,
    48,
    49,
    50,
    51,
    -1,
    -1,
    -1,
    -1,
    -1
)
private val base64EncodeChars = charArrayOf(
    'A',
    'B',
    'C',
    'D',
    'E',
    'F',
    'G',
    'H',
    'I',
    'J',
    'K',
    'L',
    'M',
    'N',
    'O',
    'P',
    'Q',
    'R',
    'S',
    'T',
    'U',
    'V',
    'W',
    'X',
    'Y',
    'Z',
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'l',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z',
    '0',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '+',
    '/'
)