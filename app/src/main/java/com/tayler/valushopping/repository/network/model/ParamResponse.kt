package com.tayler.valushopping.repository.network.model

import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.utils.EMPTY_VALE

data class ParamResponse (
    @SerializedName("uid")
    var uid : String? = EMPTY_VALE,
    @SerializedName("title")
    var title : String? = "Los Ángeles Azules, Emilia - Perdonarte ¿Para Qué?",
    @SerializedName("description")
    var description : String? = "Se estrenó el 23 may 2024\n" +
            "Music video by Los Ángeles Azules, Emilia performing Perdonarte ¿Para Qué?. (P) 2024 Promotodo México S.A. de C.V. (OCESA Seitrack)" +
            "Se estrenó el 23 may 2024\n" +
            "Music video by Los Ángeles Azules, Emilia performing Perdonarte ¿Para Qué?. (P) 2024 Promotodo México S.A. de C.V. (OCESA Seitrack)" +
            "Se estrenó el 23 may 2024\n" +
            "Music video by Los Ángeles Azules, Emilia performing Perdonarte ¿Para Qué?. (P) 2024 Promotodo México S.A. de C.V. (OCESA Seitrack)" +
            "Se estrenó el 23 may 2024\n" +
            "Music video by Los Ángeles Azules, Emilia performing Perdonarte ¿Para Qué?. (P) 2024 Promotodo México S.A. de C.V. (OCESA Seitrack)",
    @SerializedName("idMovie")
    var idMovie : String? = "beH6uqy6Xsw",
    @SerializedName("enableOther")
    var enableOther : Boolean? = false
)