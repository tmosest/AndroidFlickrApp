package com.tmosest.androidflckr

class Photo(val title: String, val author: String, val authorId: String, val links: String, val tag: String, val image: String) {
    override fun toString(): String {
        return "Photo(title: $title, author: $author, authorId: $authorId, link: $links, tag: $tag, image: $image)"
    }
}
