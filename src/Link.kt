class Link(val label: String, val url: Int? = null) {

    constructor(label: Int, url: Int? = null) : this(label.toString(), url)
}