package spaceredux

import com.soywiz.korge.view.*
import com.soywiz.korge.view.tiles.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

enum class SpaceShooterReduxBackground(val file: String) {
	BLACK("black.png"), BLUE("blue.png"), DARK_PURPLE("darkPurple.png"), PURPLE("purple.png")
}

inline class SpaceShooterReduxView<T : View>(val genericView: View) {
	@Suppress("UNCHECKED_CAST")
	val view get() = genericView as T
}

val <T : View> T.spaceShooterRedux get() = SpaceShooterReduxView<T>(this)

suspend fun SpaceShooterReduxView<out Container>.putBackground(kind: SpaceShooterReduxBackground = SpaceShooterReduxBackground.BLACK): View {
	val file = resourcesVfs["space-shooter-redux/bgs/${kind.file}"]
	val tileset = TileSet(mapOf(0 to file.readBitmap().toBMP32().slice()))
	val tilemap = view.tileMap(Bitmap32(1, 1), repeatX = TileMap.Repeat.REPEAT, repeatY = TileMap.Repeat.REPEAT, tileset = tileset)
	return tilemap
}
