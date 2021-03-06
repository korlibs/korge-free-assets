package spaceredux

import com.soywiz.klock.*
import com.soywiz.korge.resources.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tiles.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.bitmap.effect.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korim.paint.*
import com.soywiz.korim.text.*
import com.soywiz.korio.file.std.*
import com.soywiz.korio.resources.*

inline class SpaceShooterReduxWrapper<T : View>(val genericView: View) {
	@Suppress("UNCHECKED_CAST")
	val view get() = genericView as T
}

val <T : View> T.spaceShooterRedux get() = SpaceShooterReduxWrapper<T>(this)

class SpaceShooterReduxBackground(
	val view: TileMap,
	var speedX: Double = 2.0,
	var speedY: Double = 1.0,
) {
	init {
		view.addFixedUpdater(16.milliseconds) {
			view.x += speedX
			view.y += speedY
		}
	}
	enum class Type(val file: String) {
		BLACK("black.png"),
		BLUE("blue.png"),
		DARK_PURPLE("darkPurple.png"),
		PURPLE("purple.png")
	}
}
suspend fun SpaceShooterReduxWrapper<out Container>.putBackground(
	kind: SpaceShooterReduxBackground.Type = SpaceShooterReduxBackground.Type.BLACK,
	speedX: Double = 2.0,
	speedY: Double = 1.0
): SpaceShooterReduxBackground {
	val file = resourcesVfs["space-shooter-redux/bgs/${kind.file}"]
	val tileset = TileSet(mapOf(0 to file.readBitmap().toBMP32().slice()))
	val tilemap = view.tileMap(Bitmap32(1, 1), repeatX = TileMap.Repeat.REPEAT, repeatY = TileMap.Repeat.REPEAT, tileset = tileset)
	return SpaceShooterReduxBackground(tilemap, speedX, speedY)
}

suspend fun SpaceShooterReduxWrapper<out Container>.putDemoCode() {
	putBackground(SpaceShooterReduxBackground.Type.BLUE)
	view.text("space-shooter-redux!", textSize = 24.0, alignment = TextAlignment.MIDDLE_CENTER, font = resources().spaceShooterReduxResources.vectorFutureFontBitmap)
		.position(view.root.width / 2, view.root.height / 2)
		//.centerOnStage()
}

inline class SpaceReduxResourcesContainer(private val container: ResourcesContainer) : ResourcesContainer {
	override val resources get() = container.resources
}

val ResourcesContainer.spaceShooterReduxResources get() = SpaceReduxResourcesContainer(this)

val SpaceReduxResourcesContainer.vectorFutureFontTtf by resourceFont("space-shooter-redux/fonts/kenvector_future.ttf")

val SpaceReduxResourcesContainer.vectorFutureFontBitmap by resourceGlobal {
	spaceShooterReduxResources.vectorFutureFontTtf.get()
		.toBitmapFont(
			fontSize = 32,
			paint = LinearGradientPaint(0, 0, 0, 16)
				.addColorStop(0.0, Colors.YELLOW)
				.addColorStop(1.0, Colors.YELLOWGREEN),
			effect = BitmapEffect(dropShadowRadius = 2, dropShadowX = 1, dropShadowY = 1)
		)
}
