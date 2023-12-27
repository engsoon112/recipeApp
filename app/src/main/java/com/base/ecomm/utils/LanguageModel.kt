package com.base.ecomm.utils

import java.io.Serializable
import java.util.*

class LanguageModel : Serializable {

    constructor()

    constructor(languageName: String, languageCode: String, local: Locale) {

        this.languageName = languageName
        this.languageCode = languageCode
        this.local = local

    }

    var languageName = "English"
    var languageCode = "en"
    var local: Locale = Locale.ENGLISH

}