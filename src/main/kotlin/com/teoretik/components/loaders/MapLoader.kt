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
import com.teoretik.components.FloorLayer

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
            val groupLayer = if (isFloor) FloorLayer() else MapGroupLayer()

            loadBasicLayerInfo(groupLayer, element)
            val properties = element.getChildByName("properties")
            if (properties != null) {
                loadProperties(groupLayer.properties, properties)
            }
            var i = 0
            val j = element.childCount
            while (i < j) {
                val child = element.getChild(i)
                loadLayer(map, groupLayer.layers, child, tmxFile, imageResolver)
                i++
            }
            for (layer in groupLayer.layers) {
                layer.parent = groupLayer
            }
            parentLayers.add(groupLayer)
        }

        super.loadLayerGroup(map, parentLayers, element, tmxFile, imageResolver)
    }

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
        try {
            return super.castProperty(name, value, type)
        } catch (e : Exception) {
            throw GdxRuntimeException(
                "Wrong type given for property $name, given : $type, supported : string, bool, int, float, color, class"
            )
        }
    }
}