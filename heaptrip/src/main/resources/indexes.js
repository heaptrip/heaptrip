use heaptrip
db.contents.ensureIndex({_class : 1, created : 1, allowed : 1})
db.contents.ensureIndex({_class : 1, rating : 1, allowed : 1})
db.contents.ensureIndex({_class:1,'owner._id':1,created:1})
db.contents.ensureIndex({_class:1,'owner._id':1,rating:1})
