const express = require('express');
const mongoose = require('mongoose');

const app = express();
app.use(express.json());
app.use(express.json());

app.get('/', (req, res) => res.send('API running'));

mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true})
    .then(() => console.log('MongoDB connected'))
    .catch(err => console.error(err));

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server started on port ${PORT}`));