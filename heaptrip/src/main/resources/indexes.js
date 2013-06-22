use heaptrip
db.contents.ensureIndex({_class: 1, created: 1, allowed: 1})
db.contents.ensureIndex({_class: 1, rating: 1, allowed: 1})
db.contents.ensureIndex({created: 1, allowed: 1})
db.contents.ensureIndex({rating: 1, allowed: 1})
db.contents.ensureIndex({_class: 1,'owner._id': 1, created: 1})
db.contents.ensureIndex({_class: 1,'owner._id': 1, rating: 1})
db.members.ensureIndex({tripId: 1, tableId: 1})
db.members.ensureIndex({_class:1, userId: 1})
db.favorites.ensureIndex({userId: 1, type: 1})
db.favorites.ensureIndex({userId: 1, contentId: 1})
db.comments.ensureIndex({target: 1, fullSlug: 1})

