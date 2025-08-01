const express = require('express');
const Media = require('../models/Media');
const User = require('../models/User');
const router = express.Router();

// Test Route
router.get('/', (req, res) => {
  res.json({ message: 'Media route working' });
});

// @route POST /api/media/add
// @desc Adds media to a user
// @in 
// @out Result Message
router.post('/add', async (req, res) => {
  try{
    const {
      title,
      mediaType,
      creator,
      userId,
      genre,
      releaseYear,
      userRating,
      isbn,
      pageCount,
      runTimeMinutes,
      platform
    } = req.body;

    if(!title || !mediaType || !creator || !userId){
      return res.status(400).json({ message: "Missing required fields"});
    }

    const user = await User.findById(userId);
    if(!user){
     return res.status(404).json({ message: "User not found"}); 
    }

    const media = new Media({
      title,
      mediaType,
      creator,
      user: userId,
      genre,
      releaseYear,
      userRating,
      isbn,
      pageCount,
      runTimeMinutes,
      platform,
    });

    await media.save();
    res.status(200).json({ message: "Media added successfully", media});

  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: err.message});
  }
});

// @route POST /api/media/delete
// @desc Adds media to a user
// @in mediaID and userId
// @out Result Message
router.post('/delete', async (req, res) => {
  try{
    const { mediaId, userId } = req.body;

    if(!mediaId || !userId){
      return res.status(400).json({ message: "Missing mediaId or userId"});
    }

    const media = await Media.findById(mediaId);
    if(!media){
      return res.status(400).json({ message: "Entry not Found"});
    }

    if(media.user.toString() !== userId){
      return res.status(403).json({ message: "You do not have persmission to delete this entry"});
    }

    await Media.deleteOne({ _id: mediaId});
    res.status(200).json({ message: "media detleted successfully"});
  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: error.message});
  }
});

// @route POST /api/media/search
// @desc Adds media to a user
// @in  search params
// @out search results
router.post('/search', (req, res) =>{
  res.json({message: "search media"})
  try{

  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: error.message});
  }
});

// @route POST /api/media/Update
// @desc Adds media to a user
// @in updated information
// @out Result Message and list reload
router.post('/update', (req, res) => {
  res.json({message: "updateMedia"});
  try{

  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: error.message});
  }
});

module.exports = router;