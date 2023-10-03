import com.finalbossblues.timefantasy.EvilTransformingVampires
import korlibs.event.Key
import korlibs.image.atlas.MutableAtlasUnit
import korlibs.korge.Korge
import korlibs.korge.input.*
import korlibs.korge.scene.Scene
import korlibs.korge.scene.sceneContainer
import korlibs.korge.view.*
import korlibs.korge.view.animation.*
import korlibs.time.milliseconds

suspend fun main() = Korge {
    sceneContainer().changeTo({ MainMyModuleScene() })
}

class MainMyModuleScene : Scene() {
    data class ArrowKeys(
        val up: Key, val left: Key, val right: Key, val down: Key,
    ) {
        companion object {
            val STD = ArrowKeys(up = Key.UP, left = Key.LEFT, right = Key.RIGHT, down = Key.DOWN)
            val WASD = ArrowKeys(up = Key.W, left = Key.A, right = Key.D, down = Key.S)
        }
    }

    override suspend fun SContainer.sceneMain() {
        val atlas = MutableAtlasUnit(2048, 2048)
        val characters = EvilTransformingVampires.readImages(atlas)
        val player = imageDataView(characters.vampire, EvilTransformingVampires.Animations.DOWN, playing = true, smoothing = false)
            .scale(4, 4)
            .xy(120, 120)
        val player2 = imageDataView(characters.vamp, EvilTransformingVampires.Animations.DOWN, playing = true, smoothing = false)
            .scale(4, 4)
            .xy(240, 120)

        fun update(player: ImageDataView, arrows: ArrowKeys) {
            val mx = if (input.keys[arrows.left]) -1 else if (input.keys[arrows.right]) +1 else 0
            val my = if (input.keys[arrows.up]) -1 else if (input.keys[arrows.down]) +1 else 0
            if (mx == 0 && my == 0) player.stop() else player.play()
            when {
                mx < 0 -> player.animation = EvilTransformingVampires.Animations.LEFT
                mx > 0 -> player.animation = EvilTransformingVampires.Animations.RIGHT
                my < 0 -> player.animation = EvilTransformingVampires.Animations.UP
                my > 0 -> player.animation = EvilTransformingVampires.Animations.DOWN
            }
            player.zIndex = player.y
        }

        fun KeysEvents.configureArrows(player: View, arrows: ArrowKeys) {
            downFrame(arrows.left, 16.milliseconds) { player.x -= 4 }
            downFrame(arrows.right, 16.milliseconds) { player.x += 4 }
            downFrame(arrows.up, 16.milliseconds) { player.y -= 4 }
            downFrame(arrows.down, 16.milliseconds) { player.y += 4 }
        }

        addUpdater {
            update(player, ArrowKeys.STD)
            update(player2, ArrowKeys.WASD)
        }
        keys {
            configureArrows(player, ArrowKeys.STD)
            configureArrows(player2, ArrowKeys.WASD)
        }
    }
}
