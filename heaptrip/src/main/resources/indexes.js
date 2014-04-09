// TODO konovalov: check content indexes
db.contents.ensureIndex({_class: 1, created: 1, allowed: 1});
db.contents.ensureIndex({_class: 1, 'rating.value': 1, allowed: 1});
db.contents.ensureIndex({created: 1, allowed: 1});
db.contents.ensureIndex({'rating.value': 1, allowed: 1});
db.contents.ensureIndex({'ownerId': 1, _class: 1, created: 1});
db.contents.ensureIndex({'ownerId': 1, _class: 1, 'rating.value': 1});
db.contents.ensureIndex({_id: 1, 'views.ids': 1});
db.contents.ensureIndex({'favorites.ids': 1});
db.contents.ensureIndex({_id: 1, 'favorites.ids': 1});
db.contents.ensureIndex({_class: 1, 'favorites.ids': 1});
db.members.ensureIndex({contentId: 1, tableId: 1});
db.members.ensureIndex({userId: 1, contentId: 1});
db.comments.ensureIndex({target: 1, fullSlug: 1});
db.images.ensureIndex({target: 1, uploaded: 1});
db.notifications.ensureIndex({toId: 1, created: -1});
db.ratings.ensureIndex({targetId: 1, userId: 1});
db.ratings.ensureIndex({targetId: 1, created: 1});
db.regions.ensureIndex({_id: 1, population: 1});
