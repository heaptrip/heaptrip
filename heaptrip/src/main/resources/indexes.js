db.contents.ensureIndex({categoryIds: 1, created: -1, _class: 1});
db.contents.ensureIndex({categoryIds: 1, 'rating.value': -1, _class: 1});
db.contents.ensureIndex({regionIds: 1, created: -1, _class: 1});
db.contents.ensureIndex({regionIds: 1, 'rating.value': -1, _class: 1});
db.contents.ensureIndex({allowed: 1, created: -1, _class: 1});
db.contents.ensureIndex({allowed: 1, 'rating.value': -1, _class: 1});
db.contents.ensureIndex({'ownerId': 1, created: -1, _class: 1});
db.contents.ensureIndex({'ownerId': 1, 'rating.value': -1, _class: 1});
db.contents.ensureIndex({'favorites.ids': 1, _id: 1});
db.contents.ensureIndex({'favorites.ids': 1, _class: 1});
db.images.ensureIndex({target: 1, uploaded: -1, type: 1});
db.members.ensureIndex({contentId: 1, tableId: 1});
db.members.ensureIndex({userId: 1, contentId: 1});
db.comments.ensureIndex({target: 1, fullSlug: 1});
db.notifications.ensureIndex({fromId: 1, toId: 1, created: -1});
db.notifications.ensureIndex({toId: 1, created: -1});
db.notifications.ensureIndex({accountIds: 1, created: -1});
db.ratings.ensureIndex({targetId: 1, userId: 1});
db.ratings.ensureIndex({targetId: 1, created: 1});
db.relations.ensureIndex({fromId: 1, type: 1});
db.relations.ensureIndex({fromId: 1, userIds: 1, type: 1});
