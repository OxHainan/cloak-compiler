module.exports = {
    stringToHex: function (str)
    {
        const buf = Buffer.from(str, 'utf8');
        return buf.toString('hex');
    },
    
    hexToString: function (str)
    {
        const buf = Buffer.from(str, 'hex');
        return buf.toString('utf8');
    },

    stringToBase64: function (str)
    {
        const buf = Buffer.from(str, 'utf8');
        return buf.toString('base64');
    },

    hexToBase64: function (str)
    {
        const buf = Buffer.from(str, 'hex');
        return buf.toString('base64');
    }
}
