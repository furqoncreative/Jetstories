package id.furqoncreative.jetstories

import id.furqoncreative.jetstories.model.stories.Story

object DataDummy {

    fun generateDummyStory(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val storyItem = Story(
                id = i.toString(),
                createdAt = "",
                description = "desc $i",
                name = "name $i",
                lat = 0.0,
                lon = 0.0,
                photoUrl = "https://photo.com/$i"
            )
            items.add(storyItem)
        }
        return items
    }
}