# 🌱🧟 P&Z Tower Defense

P&Z Tower Defense is a **Java Swing tower defense game** inspired by Plants vs Zombies.  
The project showcases **OOP**, **event handling**, **collision detection**, and a modular **MVC-inspired architecture**.

---

## 🌐 Overview
A simple but complete gameplay loop:

**☀️ Collect Sun → 🌿 Place Plants → 🧟 Zombies Spawn → 🌊 Waves → 🏆 Win / ❌ Lose**

The game includes 2 lanes, multiple plant types, zombies, and basic strategy mechanics.

---

## 🎮 Features
- ☀️ **Sun Collection System**
- 🌱 **Place Plants** (Sunflower, Peashooter, Freeze Pea)
- 🧟 **Zombies march across lanes**
- 💥 **Collision detection** (bullets ↔ zombies / zombies ↔ plants)
- 🌊 **Wave system**
- 🏁 **Win/Lose condition**
- 🖼️ **Java Swing GUI** using `JPanel`, `JFrame`, `Graphics2D`

---

## 🏗️ Architecture

The project follows a **variant of MVC**, split into clear packages:


A simple breakdown of the main folders:
- **view/** – handles the game interface and rendering.
- **core/** – contains the main game logic and update loop.
- **plants/** – all plant-related classes.
- **zombies/** – all zombie-related classes.
- **utils/** – utilities such as projectiles and items created by plants.



### 🔧 Design Highlights
- 🧱 Object-Oriented entity hierarchy  
- 🎨 Rendering handled separately from logic  
- ⏱️ Timer-based game loop for animations  
- ♻️ Entity Manager for plants, zombies, and projectiles  

---

## 🎥 Demo

[**Video Demo: P&Z Tower Defense**](https://github.com/user-attachments/assets/28b27a2c-f142-4f41-a741-1d69167236d8)

---

## 💻 Technology Stack

| Category | Tools | Description |
|---------|--------|-------------|
| **Language** | Java | Game logic & rendering |
| **GUI** | Swing, AWT | UI, panels, events |
| **Architecture** | MVC-like | Clear separation of logic & view |
| **Version Control** | GitHub | Collaboration & tracking |

---

## 🧩 Gameplay Loop
1. ☀️ Collect sun drops  
2. 🌿 Choose and place plants on tiles  
3. 🧟 Zombies spawn and walk in lanes  
4. 💥 Plants shoot and deal damage  
5. 🌊 Wave progresses  
6. 🏆 Win if zombies are stopped  
7. ❌ Lose if a zombie reaches the base

---

## 📬 Contact

**Leader:** Minh Mẫn  
📧 Email: **phamminhman1312005@gmail.com**  
🔗 GitHub: **MinhMan1301** — https://github.com/MinhMan1301  
🔗 LinkedIn: **Minh Mẫn** — https://www.linkedin.com/in/minh-m%E1%BA%ABn-47b493311/

---

## 📄 License
This project is for **educational and academic purposes**.



