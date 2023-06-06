package com.teoretik.components.loaders

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.XmlReader

class MapLoader : TmxMapLoader() {
    override fun loadProperties(properties: MapProperties?, element: XmlReader.Element?) {
        if (element == null) return
        if (element.name == "properties") {
            for (property in element.getChildrenByName("property")) {
                val name = property.getAttribute("name", null)
                var value = property.getAttribute("value", null)
                val type = property.getAttribute("type", null)
                if (value == null) {
                    value = property.text
                }
                var castValue : Any?
                if (type == "class") {
                    castValue = MapProperties()
                    property.getChildByName("properties").apply {
                        this.getChildrenByName("property").forEach {
                            loadProperties(castValue as MapProperties, it)
                        }
                    }
                } else {
                    castValue = castProperty(name, value, type)
                }
                properties!!.put(name, castValue)
            }
        }
    }

    override fun castProperty(name: String?, value: String?, type: String?): Any {
        return when (type) {
            null -> value!!
            "int" -> Integer.valueOf(value)
            "float" -> java.lang.Float.valueOf(value)
            "bool" -> java.lang.Boolean.valueOf(value)
            "color" -> {
                // Tiled uses the format #AARRGGBB
                val opaqueColor = value!!.substring(3)
                val alpha = value.substring(1, 3)
                Color.valueOf(opaqueColor + alpha)
            }
            else -> throw GdxRuntimeException(
                "Wrong type given for property $name, given : $type, supported : string, bool, int, float, color"
            )
        }

    }
}