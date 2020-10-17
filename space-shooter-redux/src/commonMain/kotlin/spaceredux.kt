package spaceredux

inline class SpaceShooterReduxView<T : View>(val genericView: View) {
	@Suppress("UNCHECKED_CAST")
	val view get() = genericView as T
}

val <T : View> T.spaceShooterRedux get() = SpaceShooterReduxView<T>(this)

//suspend fun SpaceShooterReduxView<out Container>.putBackground(): TileMap {
suspend fun SpaceShooterReduxView<out Container>.putBackground(): Image {
	return view.image(resourcesVfs["space-shooter-redux/bgs/blue.png"].readBitmap())
	//val tileset = TileSet(mapOf(0 to resourcesVfs["space-shooter-redux/bgs/blue.png"].readBitmap().toBMP32().scaleLinear(0.5, 0.5).slice()))
	//val tileset = TileSet(mapOf(0 to resourcesVfs["space-shooter-redux/bgs/blue.png"].readBitmap().slice()))
	//val tilemap = view.tileMap(Bitmap32(1, 1), repeatX = TileMap.Repeat.REPEAT, repeatY = TileMap.Repeat.REPEAT, tileset = tileset)
	//return tilemap
}
