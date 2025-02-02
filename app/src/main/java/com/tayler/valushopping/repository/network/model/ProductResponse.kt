package com.tayler.valushopping.repository.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.BuildConfig
import com.tayler.valushopping.utils.EMPTY_VALE
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(
    @SerializedName("uid")
    var uid: String? = EMPTY_VALE,
    @SerializedName("name")
    var name: String? = EMPTY_VALE,
    @SerializedName("description")
    var description: String? = EMPTY_VALE,
    @SerializedName("type")
    var type: String? = EMPTY_VALE,
    @SerializedName("category")
    var category: String? = EMPTY_VALE,
    @SerializedName("price")
    var price: String? = EMPTY_VALE,
    @SerializedName("priceTwo")
    var priceTwo: String? = EMPTY_VALE,
    @SerializedName("state")
    var state: Boolean? = true,
    @SerializedName("img")
    var img: String? = EMPTY_VALE,
    @SerializedName("gender")
    var gender: String? = EMPTY_VALE,
    @SerializedName("phone")
    var phone: String? = EMPTY_VALE,
    @SerializedName("principal")
    var principal: Boolean? = false,
    @SerializedName("admin")
    var admin: Boolean? = false,
    @SerializedName("idUser")
    var idUser: String? = EMPTY_VALE
) : Parcelable {

    fun getLoadImage() = "${BuildConfig.BASE_URL}uploads/product/${img}"
    fun getPriceUnit() = "S/ $price"

    fun getPriceDoc() = "S/ $priceTwo"

    fun getPriceUnitTwo() = "c/u: S/ $price"

    fun getPriceDocTwo(): String {
        return if (visiblePriceDoc()) {
            "doc/: S/ $priceTwo"
        } else {
            "doc/: No disponible"
        }
    }

    fun visiblePriceDoc() = priceTwo?.isNotEmpty() == true && priceTwo != "0.00"

    fun getMapperTypeAndGender(): String {
        return "${getMapperType()}  ${getMapperGender()}"
    }

    private fun getMapperType(): String {
        return when (type) {
            "0" -> "Visuteria"
            "1" -> "Polo"
            "2" -> "Pantalon"
            "3" -> "Calzado"
            "4" -> "Falda"
            "5" -> "Prendas"
            "6" -> "Accesorios"
            "7" -> "Otros"
            else -> "Otros"
        }

    }

    private fun getMapperGender(): String {
        return when (gender) {
            "0" -> "Mujer"
            "1" -> "Varon"
            "2" -> "Unisex"
            else -> "Mujer"
        }
    }
}