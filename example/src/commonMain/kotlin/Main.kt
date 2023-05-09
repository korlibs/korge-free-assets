import com.finalbossblues.timefantasy.EvilTransformingVampires
import korlibs.event.Key
import korlibs.image.atlas.MutableAtlasUnit
import korlibs.korge.Korge
import korlibs.korge.input.keys
import korlibs.korge.scene.Scene
import korlibs.korge.scene.sceneContainer
import korlibs.korge.view.SContainer
import korlibs.korge.view.addUpdater
import korlibs.korge.view.animation.imageDataView
import korlibs.korge.view.scale
import korlibs.korge.view.xy
import korlibs.time.milliseconds

suspend fun main() = Korge {
    sceneContainer().changeTo({ MainMyModuleScene() })
}

class MainMyModuleScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val atlas = MutableAtlasUnit(2048, 2048)
        val characters = EvilTransformingVampires.readImages(atlas)
        val player = imageDataView(characters.vampire, EvilTransformingVampires.Animations.DOWN, playing = true, smoothing = false)
            .scale(4, 4)
            .xy(120, 120)

        fun update() {
            val mx = if (input.keys[Key.LEFT]) -1 else if (input.keys[Key.RIGHT]) +1 else 0
            val my = if (input.keys[Key.UP]) -1 else if (input.keys[Key.DOWN]) +1 else 0
            if (mx == 0 && my == 0) player.stop() else player.play()
            when {
                mx < 0 -> player.animation = EvilTransformingVampires.Animations.LEFT
                mx > 0 -> player.animation = EvilTransformingVampires.Animations.RIGHT
                my < 0 -> player.animation = EvilTransformingVampires.Animations.UP
                my > 0 -> player.animation = EvilTransformingVampires.Animations.DOWN
            }
        }

        addUpdater {
            update()
        }
        keys {
            downFrame(Key.LEFT, 16.milliseconds) { player.x -= 4 }
            downFrame(Key.RIGHT, 16.milliseconds) { player.x += 4 }
            downFrame(Key.UP, 16.milliseconds) { player.y -= 4 }
            downFrame(Key.DOWN, 16.milliseconds) { player.y += 4 }
        }
    }
}
