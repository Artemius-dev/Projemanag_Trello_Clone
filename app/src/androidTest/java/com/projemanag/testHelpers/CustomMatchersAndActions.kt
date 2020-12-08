package com.projemanag.testHelpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import com.projemanag.robots.BaseTestRobot
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import kotlin.math.exp


fun withViewAtPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description?) {
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder =
                recyclerView.findViewHolderForAdapterPosition(position)
            return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
        }

    }
}

fun clickItemWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Action Description"
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById<View>(id) as View
            // your action
            v.performClick()
        }
    }
}

fun enterTextOnViewWithId(text: String, id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Action Description"
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById<View>(id) as EditText
            // your action
            v.setText(text)
        }
    }
}

fun clickOnCardListView(id: Int, position: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Action Description"
        }

        override fun perform(uiController: UiController, view: View) {
            val cardList = view.findViewById<View>(id) as RecyclerView

            cardList[position].performClick()

        }
    }
}

//class DrawableMatcher internal constructor(private val expectedId: Int) :
//    TypeSafeMatcher<View>(View::class.java) {
//    private var resourceName: String? = null
//    override fun matchesSafely(target: View): Boolean {
//        if (target !is ImageView) {
//            return false
//        }
//        val imageView = target
//
//        if (expectedId == EMPTY) {
//            return imageView.drawable == null
//        }
//        if (expectedId == ANY) {
//            return imageView.drawable != null
//        }
//        val resources = target.getContext().resources
//        val expectedDrawable =
//            resources.getDrawable(expectedId)
//        resourceName = resources.getResourceEntryName(expectedId)
//        if (expectedDrawable == null) {
//            return false
//        }
//        val bitmap = getBitmap(imageView.drawable)
//        val vector = getBitmapFromVectorDrawable(BaseTestRobot().context, expectedId)
//        val otherBitmap = getBitmap(expectedDrawable)
//        return bitmap.sameAs(otherBitmap)
//    }
//
//    private fun getBitmap(drawable: Drawable): Bitmap {
//        val bitmap = Bitmap.createBitmap(
//            drawable.intrinsicWidth,
//            drawable.intrinsicHeight,
//            Bitmap.Config.ARGB_8888
//        )
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
//        drawable.draw(canvas)
//        return bitmap
//    }
//
//    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
//        var drawable =
//            AppCompatResources.getDrawable(context, drawableId)
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            drawable = DrawableCompat.wrap(drawable!!).mutate()
//        }
//        val bitmap = Bitmap.createBitmap(
//            drawable!!.intrinsicWidth,
//            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
//        )
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, canvas.width, canvas.height)
//        drawable.draw(canvas)
//        return bitmap
//    }
//
//    override fun describeTo(description: Description) {
//        description.appendText("with drawable from resource id: ")
//        description.appendValue(expectedId)
//        if (resourceName != null) {
//            description.appendText("[")
//            description.appendText(resourceName)
//            description.appendText("]")
//        }
//    }
//
//    companion object {
//        const val EMPTY = -1
//        const val ANY = -2
//    }
//
//}

//object CustomMatchers {
//
//    fun withIcon(@DrawableRes resourceId: Int): Matcher<View> {
//        return object : BoundedMatcher<View, ActionMenuItemView>(ActionMenuItemView::class.java) {
//            override fun describeTo(description: Description) {
//                description.appendText("has image drawable resource $resourceId")
//            }
//
//            public override fun matchesSafely(actionMenuItemView: ActionMenuItemView): Boolean {
//                return sameBitmap(actionMenuItemView.context, actionMenuItemView.itemData.icon, resourceId)
//            }
//        }
//    }
//
//    private fun sameBitmap(context: Context, drawable: Drawable?, resourceId: Int): Boolean {
//        var drawable = drawable
//        var otherDrawable: Drawable? = ContextCompat.getDrawable(context, resourceId)
//        if (drawable == null || otherDrawable == null) {
//            return false
//        }
//        if (drawable is StateListDrawable && otherDrawable is StateListDrawable) {
//            drawable = drawable.current
//            otherDrawable = otherDrawable.current
//        }
//
//        val bitmap = if (drawable is VectorDrawable) vectorToBitmap(drawable)
//        else (drawable as BitmapDrawable).bitmap
//        val otherBitmap = if (otherDrawable is VectorDrawable) vectorToBitmap(otherDrawable)
//        else (otherDrawable as BitmapDrawable).bitmap
//        return bitmap.sameAs(otherBitmap)
//    }
//
//    private fun vectorToBitmap(vectorDrawable: VectorDrawable): Bitmap {
//        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
//        vectorDrawable.draw(canvas)
//        return bitmap
//    }
//}