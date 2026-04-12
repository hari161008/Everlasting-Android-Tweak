# Everlasting Android Tweak

<br>
<p align="center">
  <img src="assets/images/banner.png" width="200"/>
</p>
<br>

Everlasting Android Tweak is the true heart of Android, powerful, feature rich toolkit designed to unlock the full potential of your Android device. It provides you many advanced gestures, customization, system controls, and smart utilities to enhance and personalize your device experience.<br>
<br>
<body>
  <center>
    <b>Think Different :)</b>
  </center>
</body>

<br>

# ⭐ Rate and Review 
Everyone's Ratings: [Click Here](https://docs.google.com/spreadsheets/d/1s18HvfSKTFTIWTc6WDoBoE5SHFmLXdLcUmMdtx_Q1UY/edit?resourcekey=&gid=679995764#gid=679995764)

<br>

<div class="reviews" id="reviews"></div>

<style>
.reviews {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 20px;
  padding: 20px;
}

.review-card {
  background: #ffffff;
  padding: 20px;
  border-radius: 18px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.08);
  transition: 0.3s ease;
}

.review-card:hover {
  transform: translateY(-6px);
}

.review-card h3 {
  margin: 0 0 10px;
  font-size: 18px;
}

.review-card p {
  margin: 10px 0;
  color: #444;
}

.review-card span {
  font-size: 14px;
  color: gray;
}
</style>

<script>
const sheetURL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vQgvvFcO2C7gQQQyu6Y1hk_Az6e9AMa8vgyiQxPOlAj6oH0s_rajS8c92C1iEXGeO1BOQ-Qq3cY3wPp/pub?output=csv";

fetch(sheetURL)
  .then(res => res.text())
  .then(csv => {
    const rows = csv.split("\n").slice(1); // remove header
    const container = document.getElementById("reviews");

    rows.forEach(row => {
      const cols = row.split(",");

      if(cols.length < 3) return;

      const name = cols[0];
      const rating = parseInt(cols[1]);
      const review = cols[2];

      const stars = "⭐".repeat(rating);

      const card = document.createElement("div");
      card.className = "review-card";

      card.innerHTML = `
        <h3>${stars}</h3>
        <p>"${review}"</p>
        <span>- ${name}</span>
      `;

      container.appendChild(card);
    });
  });
</script>


<br>

If you would like to write a Review, you must install the app, then go to App's settings > Rate and Review

<br>

# 📱 Try this app without actually installing
Demo App: [Click Here](https://hari161008.github.io/Website-For-Everlasting-Android-Tweak/Everlasting_Android_Tweak_Demo%20v2.html) <br> <br>
NOTE: THE DEMO APP IS JUST SIMILAR NOT EXACTLY THE SAME IN LOOKS ALONE, LOOK SCREENSHOTS BELOW FOR EXACT DETAILS

<br>

# 📦 Latest Releases
GitHub: [Download](https://github.com/hari161008/Everlasting-Android-Tweak/releases) ⬇️

<br>

# 🚀 Telegram Links

🌐 Cool Apps Recommending Channel:
<a href="https://t.me/CoolAppStore">Join Now</a>
<br>
📢 Everlasting Support Group:
<a href="https://t.me/EverlastingAndroidTweak">Join Now</a>

<br>

# 📱 Screenshots:

<p align="center">
  <img src="assets/images/screenshots/1.png" width="32%"/>
  <img src="assets/images/screenshots/2.png" width="32%"/>
  <img src="assets/images/screenshots/3.png" width="32%"/>
</p>

<p align="center">
  <img src="assets/images/screenshots/4.png" width="32%"/>
  <img src="assets/images/screenshots/5.png" width="32%"/>
  <img src="assets/images/screenshots/6.png" width="32%"/>
</p>

<p align="center">
  <img src="assets/images/screenshots/7.png" width="32%"/>
  <img src="assets/images/screenshots/8.png" width="32%"/>
  <img src="assets/images/screenshots/9.png" width="32%"/>
</p>

<p align="center">
  <img src="assets/images/screenshots/10.png" width="32%"/>
  <img src="assets/images/screenshots/11.png" width="32%"/>
  <img src="assets/images/screenshots/12.png" width="32%"/>
</p>

<br>

# Features of Everlasting Android Tweak .✦ ݁˖
┈➤ Gestures

• 🔦 Shake for Torch – Shake your phone to toggle the flashlight <br>
• 📸 Twist for Camera – Twist your wrist to quickly open the camera <br>
• 👆 Double Tap Back – Double-tap the back for torch or custom app <br>
• 🔕 Flip to DND – Flip device face-down to enable Do Not Disturb <br>
• ⚡ Charging Animation – Beautiful animation when charger connects <br>
• 📱 Custom Nav Bar – Overlay your own custom navigation bar <br>
<br>
<br>
┈➤ Power & Buttons

• 🔌 Custom Power Menu – Replace the default system power dialog <br>
• ⚡ Double Power Press – Trigger actions with two quick presses <br>
• 🔘 Screen-Off Actions – Long-press buttons even when screen is off <br>
• 🎭 Fake Power Off – Simulate a realistic fake shutdown <br>
<br>
<br>
┈➤ Audio & Haptics

• 🎚️ Built-in Equalizer – Fine-tune audio with a 5-band EQ <br>
• 🔊 Volume Booster – Increase volume beyond system limits <br>
• 🎛️ Volume Styles – Customize the volume panel UI <br>
• 📳 Custom Haptics – Adjust tap & scroll vibration feedback <br>
• 🔔 Custom Sounds – Set sounds for lock, unlock, tap & charging <br>
• ⏰ Call/Alarm Vibrations – Custom vibration patterns <br>
<br>
<br>
┈➤ Customization

• 🧩 Lock Screen Widgets – Add widgets directly to your lock screen <br>
• ⚙️ Custom QS Tiles – Add 15+ quick settings tiles <br>
• 🎨 Eye Dropper – Pick any color from your screen <br>
• 🧭 Compass – Live compass with animated UI <br>
<br>
<br>
┈➤ Visuals

• 🌌 Screensaver – Beautiful themed screensavers <br>
• 🖼️ Wallpaper Effects – Pixel-style wallpaper enhancements <br>
• 🤖 AI Image Upscaler – Upscale images to 2× or 4× <br>
• 📷 Watermark Photos – Add EXIF overlay & custom frames <br>
<br>
<br>
┈➤ Productivity

• ❄️ App Freezer – Freeze, hide, or suspend apps (via Hail) <br>
• 🔄 App Updater – Check GitHub for sideloaded app updates <br>
• 🔋 Charge Limit Alarm – Get notified at a set battery level <br>
• ⏳ Keep Screen On – Prevent screen sleep + quick tile <br>
<br>
<br>
┈➤ Device & System (Not working right now)

• 📊 Task Manager – View & kill running processes <br>
• 💻 Terminal – Run shell commands on your device <br>
• 🧹 Cache Cleaner – Clear app cache (via Shizuku) <br>
• 🔐 Shizuku Manager – Control Shizuku service & permissions <br>
• 🖥️ Secondary Display – Cast content to another screen <br>
• 🔁 Auto Reboot – Schedule automatic restarts <br>
• 🧩 Hidden Features – Unlock hidden Android settings <br>
• 🗺️ Maps Power Saving – Reduce battery usage during navigation <br>
<br>
<br>
┈➤ Security

• 🚨 Motion Alert – Get notified when your device is moved <br>
• 🔒 Screenshot Blocker – Prevent screenshots & screen recording <br>
<br>
<br>
┈➤ Communication

• 📡 Walkie Talkie – Push-to-talk via Wi-Fi Direct <br>
• 📞 Fake Call – Simulate incoming calls <br>
<br>
<br>
┈➤ Notifications & Sensors

• 💡 Notification Lighting – Edge lighting & flashlight alerts <br>
• 🔋 Battery Health – View battery stats & OEM data <br>
• 🧲 Magnetic Field Sensor – Real-time magnetic field strength <br>
• 📱 Device Info – Full hardware & software details <br>
<br>
<br>
┈➤ Music & Light

• 🎵 Music Reactive Light – Flash & vibrate to music beats <br>
• 📊 Music Leveler – Animated equalizer across the screen <br>
<br>
<br>
┈➤ AI Writing

• ✍️ SwiftSlate AI – AI-powered text replacement anywhere <br>
