package com.vectorinc.vectormoviesearch.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun convertToPercentage(voteAverage: Double): Float {
    return voteAverage.toFloat() / 10
}

fun convertToDate(date: String): String {
    if (date.toString().isBlank()) return ""
    val date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return "${date.month}" + " " + "${date.year}"
}
fun watchYoutubeVideo(context: Context, id: String) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("http://www.youtube.com/watch?v=$id")
    )
    try {
        context.startActivity(appIntent)
    } catch (ex: ActivityNotFoundException) {
        context.startActivity(webIntent)
    }
}

fun moveToDomain(url : Int, context: Context){
    val urlAppend = "https://www.themoviedb.org/movie/$url"
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(urlAppend)
    )
    try {
        context.startActivity(webIntent)
    } catch (ex: ActivityNotFoundException) {
        context.startActivity(webIntent)
    }
}

fun moveToPrivacy( context: Context){
    val urlAppend = "https://sites.google.com/view/creaview/home"
    val webIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(urlAppend)
    )
    try {
        context.startActivity(webIntent)
    } catch (ex: ActivityNotFoundException) {
        context.startActivity(webIntent)
    }
}

fun openLinkedInPage(context: Context) {
    val linkedId = "ezinwa-victor-80242719a"
    var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@$linkedId"))
    val packageManager: PackageManager = context.packageManager
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    if (list.isEmpty()) {
        intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.linkedin.com/profile/view?id=$linkedId")
        )
    }
    context.startActivity(intent)
}
