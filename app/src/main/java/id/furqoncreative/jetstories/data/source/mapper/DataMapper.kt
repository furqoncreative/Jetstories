package id.furqoncreative.jetstories.data.source.mapper

import id.furqoncreative.jetstories.data.source.local.StoryFavoriteItem
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.model.stories.Story

fun Story.toStoryItem(): StoryItem {
    return StoryItem(
        id = id,
        createdAt = createdAt,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl
    )
}

fun Story.toStoryFavoriteItem(): StoryFavoriteItem {
    return StoryFavoriteItem(
        id = id,
        createdAt = createdAt,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl
    )
}

fun StoryFavoriteItem.toStory(): Story {
    return Story(
        id = id,
        createdAt = createdAt,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl
    )
}

fun StoryItem.toStory(): Story {
    return Story(
        id = id,
        createdAt = createdAt,
        description = description,
        lat = lat,
        lon = lon,
        name = name,
        photoUrl = photoUrl
    )
}