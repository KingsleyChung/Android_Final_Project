const router = require('koa-router')();
const user = require('./user');
const task = require('./task');
const group = require('./group');

router.use('/api/user', user.routes(), user.allowedMethods());
router.use('/api/task', task.routes(), task.allowedMethods());
router.use('/api/group', group.routes(), group.allowedMethods());

module.exports = router;