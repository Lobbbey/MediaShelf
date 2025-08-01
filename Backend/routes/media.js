const express = require('express');
const router = express.Router();

// Test Route
router.get('/', (req, res) => {
  res.json({ message: 'Media route working' });
});

// @route POST /api/media/add
// @desc Adds media to a user
// @in 
// @out Result Message
router.post('/add', (req, res) => {
  res.json({message: "addmedia"});
  try{

  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: error.message});
  }
});

// @route POST /api/media/add
// @desc Adds media to a user
// @in 
// @out Result Message
router.post('/delete', (req, res) => {
  res.json({message: "delete media"});
  try{

  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: error.message});
  }
});

// @route POST /api/media/add
// @desc Adds media to a user
// @in 
// @out Result Message
router.post('/search', (req, res) =>{
  res.json({message: "search media"})
  try{

  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: error.message});
  }
});

router.post('/update', (req, res) => {
  res.json({message: "updateMedia"});
  try{

  } catch(err){
    console.error("Error:", err);
    res.status(500).json({Result: error.message});
  }
});

module.exports = router;