const express = require('express');
const mongoose = require('mongoose');
require('dotenv').config();

const app = express();
app.use(express.json());

// Import routes
const usersRoutes = require('./routes/users.js');
const mediaRoutes = require('./routes/media.js');

// Use routes
app.use('/api/users', usersRoutes);
app.use('/api/media', mediaRoutes);

app.get('/', (req, res) => res.send('API running'));

mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true})
    .then(() => console.log('MongoDB connected'))
    .catch(err => console.error(err));

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server started on port ${PORT}`));