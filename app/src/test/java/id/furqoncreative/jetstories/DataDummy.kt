package id.furqoncreative.jetstories

import id.furqoncreative.jetstories.data.source.local.StoryItem

object DataDummy {

    fun generateDummyStory(): List<StoryItem> {
        val items: MutableList<StoryItem> = arrayListOf()
        for (i in 0..100) {
            val storyItem = StoryItem(
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