package com.teoretik.graphics.resources

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable

object Batch : Disposable {
    private val batch = SpriteBatch()
    operator fun invoke() = batch
    override fun dispose() {
        batch.dispose()
    }
}