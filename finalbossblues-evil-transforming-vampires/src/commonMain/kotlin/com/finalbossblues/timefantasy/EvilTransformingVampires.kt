package com.finalbossblues.timefantasy

import korlibs.image.atlas.*
import korlibs.image.format.*
import korlibs.io.file.*
import korlibs.io.file.std.*

class EvilTransformingVampires(val imageDataContainer: ImageDataContainer) {
    val vampire get() = imageDataContainer["vampire"]
    val vamp get() = imageDataContainer["vamp"]

    object Animations {
        val LEFT = "left"
        val RIGHT = "right"
        val UP = "up"
        val DOWN = "down"
    }

    companion object {
        suspend fun readImages(atlas: MutableAtlasUnit? = null, res: VfsFile = resourcesVfs): EvilTransformingVampires {
            return EvilTransformingVampires(
                res["finalbossblues-evil-transforming-vampires/vampire.ase"].readImageDataContainer(ASE.toProps(), atlas = atlas)
            )
        }
    }
}