package com.teoretik.components.loaders

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.maps.ImageResolver
import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.MapLayers
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.XmlReader
import com.teoretik.components.map.Floor

class MapLoader : TmxMapLoader() {

    override fun loadLayerGroup(
        map: TiledMap,
        parentLayers: MapLayers,
        element: XmlReader.Element,
        tmxFile: FileHandle,
        imageResolver: ImageResolver
    ) {
        if (element.name == "group") {
            val isFloor = element.get("class", "") == "Floor"

            val groupLayer = if (isFloor) Floor() else MapGroupLayer()

            loadBasicLayerInfo(groupLayer, element)
            val properties = element.getChildByName("properties")
            loadProperties(groupLayer.properties, properties)

            (0 until element.childCount).forEach {
                val child = element.getChild(it)
                loadLayer(map, groupLayer.layers, child, tmxFile, imageResolver)
            }

            for (layer in groupLayer.layers) {
                layer.parent = groupLayer
            }

            parentLayers.add(groupLayer)
        }
    }

    override fun loadProperties(properties: MapProperties?, element: XmlReader.Element) {
        if (element.name == "properties") {
            for (property in element.getChildrenByName("property")) {
                val name = property.getAttribute("name", null)
                var value = property.getAttribute("value", null)
                val type = property.getAttribute("type", null)
                if (value == null) {
                    value = property.text
                }
                var castValue: Any?
                if (type == "class") {
                    castValue = MapProperties()
                    property.getChildByName("properties").run {
                        loadProperties(castValue as MapProperties, this)
                    }
                } else {
                    castValue = castProperty(name, value, type)
                }
                properties?.put(name, castValue)
            }
        }
    }

    override fun castProperty(name: String?, value: String?, type: String?): Any {
        try {
            return super.castProperty(name, value, type)
        } catch (e: Exception) {
            throw GdxRuntimeException(
                "Wrong type given for property $name, given : $type, supported : string, bool, int, float, color, class"
            )
        }
    }
}