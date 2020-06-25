var replace = require('replace-in-file');
var package = require("./package.json");
var buildVersion = package.version;

var moment = require('moment');
moment.locale('en-US');
var dt = new Date();
var tstamp = moment(dt).format('YYYYMMDDHHmmss');

// const tstamp = new Date().toISOString()
//     .replace(/\.[\w\W]+?$/, '') // Delete from dot to end.
//     .replace(/\:|\s|T|-/g, '');  // Replace colons, spaces, and T with hyphen.

const options = {
    files: 'src/environments/environment.prod.ts',
    from: /version: '(.*)'/g,
    to: "version: '"+ buildVersion + '_' + tstamp + "'",
    allowEmptyPaths: false,
};

try {
    let changedFiles = replace.sync(options);
    if (changedFiles == 0) {
        throw "Please make sure that file '" + options.files + "' has \"version: ''\"";
    }
    console.log('Build version set: ' + buildVersion);
}
catch (error) {
    console.error('Error occurred:', error);
    throw error
}