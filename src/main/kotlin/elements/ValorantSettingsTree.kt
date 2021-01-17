package elements

object ValorantSettingsTree {
    val categories = listOf(
        SimpleCategory(
            name = "General",
            buttonX = 720,
            startY = 132,
            windowHeight = 837,
            elements = listOf(
                section("Accessibility", height = 36),
                dropdown("Text Language").skip(),
                dropdown("Enemy Highlight Color"),

                section("Mouse"),
                slider("Sensitivity: Aim"),
                slider("Scoped Sensitivity Multiplier"),
                switch("Invert Mouse"),

                section("Map"),
                switch("Rotate"),
                switch("Fixed Orientation"),
                switch("Keep Player Centered"),
                slider("Minimap Size"),
                slider("Minimap Zoom"),
                switch("Minimap Vision Cones"),
                dropdown("Show Map Region Names"),

                section("Privacy", 75),
                switch("Hide My Name from Players Outside My Party"),
                switch("Use Generic Names for Players Outside My Party"),
                switch("Auto-Reject Friend Requests"),

                section("Other", 96),
                switch("First Person Handedness"),
                switch("Always Show Inventory"),
                switch("Player Loadouts Always Visible"),
                switch("Cycle to Next/Prev Weapon Wraps Inventory"),
                switch("Cycle to Next/Prev Weapon Includes Spike"),
                switch("Show Mature Content"),
                switch("Show Corpses"),
                switch("Show Blood"),
                switch("Explicit Language Filter"),
                switch("Instability Indicators"),
                triple("Network Buffering"),
                switch("Show Bullet Tracers"),
                switch("Show Spectator Count"),
                switch("Use Team Color for Crosshair Color"),
                switch("Hide User Interface In Game"),
                switch("Hide First Person Arms (Observers Only)"),
                switch("Show Full Screen Blind for Observers"),
                switch("Team Colors Affect HUD for Observers"),
                switch("Show Player Keybinds on Minimap"),
                slider("Observer Free Camera Fast Speed"),
                slider("Observer Free Camera Slow Speed"),
            )
        ),
        TabbedCategory(
            name = "Controls",
            buttonX = 840,
            startY = 133,
            firstElementMargin = 20,
            windowHeight = 797,
            tabs = listOf(
                Tab(name = "Movement", buttonX = 690, elementWidth = 988, elements = listOf(
                    keybind("Forward"),
                    keybind("Back"),
                    keybind("Strafe Left"),
                    keybind("Strafe Right"),
                    switch("Default Movement Mode"),
                    keybind("Walk"),
                    switch("Toggle Walk"),
                    keybind("Jump"),
                    keybind("Crouch"),
                    switch("Toggle Crouch"),
                )),
                Tab(name = "Equipment", buttonX = 865, elementWidth = 972, elements = listOf(
                    keybind("Fire"),
                    keybind("Alternate Fire"),
                    keybind("Toggle Zoom Level"),
                    switch("Aim Down Sights"),
                    switch("Sniper Rifle Aim"),
                    switch("Operator Zoom"),
                    switch("Auto Re-enter Scope"),
                    keybind("Reload"),
                    keybind("Equip Primary Weapon"),
                    keybind("Equip Secondary Weapon"),
                    keybind("Equip Melee Weapon"),
                    keybind("Equip Spike"),
                    keybind("Cycle to Next Weapon"),
                    keybind("Cycle to Previous Weapon"),
                    keybind("Drop Equipped Item"),
                    keybind("Equip Last Used Item"),
                    keybind("Inspect Weapon"),
                    keybind("Use Object"),
                    keybind("Use Spike (Plant or Defuse)"),
                    keybind("Use/Equip Ability: 1"),
                    keybind("Use/Equip Ability: 2"),
                    keybind("Use/Equip Ability: 3"),
                    keybind("Use/Equip Ability: Ultimate"),
                    keybind("Use Spray"),
                )),
                Tab(name = "Communication", buttonX = 1050, elementWidth = 972, elements = listOf(
                    section("Voice Chat", height = 39),
                    singleKeybind("Party Voice Push To Talk Key"),
                    singleKeybind("Team Voice Push To Talk Key"),

                    section("Pings", height = 70),
                    keybind("Ping (Tap) / Ping Wheel (Hold)"),
                    slider("Ping Wheel Hold Delay milliseconds"),
                    keybind("Ping: Caution"),
                    keybind("Ping: Watching here"),
                    keybind("Ping: Need Support"),
                    keybind("Ping: On My Mark"),

                    section("Radio Commands Menu", height = 70),
                    keybind("Radio Commands Menu Index"),
                    keybind("Combat Radio Commands Menu"),
                    keybind("Tactics Radio Commands Menu"),
                    keybind("Social Radio Commands Menu"),
                    keybind("Strategy Radio Commands Menu"),

                    section("Radio Commands Wheel", height = 70),
                    keybind("Radio Command Wheel Index"),
                    keybind("Combat/Tactics Radio Commands Wheel"),
                    keybind("Social Radio Commands Wheel"),
                    keybind("Strategy Radio Commands Wheel"),
                )),
                Tab(name = "Interface", buttonX = 1235, elements = listOf(
                    section("General", height = 49),
                    keybind("Show Teammate Loadouts (Hold)"),
                    keybind("Toggle Menus"),
                    keybind("Combat Report"),
                    keybind("Agent Ability Tooltip"),
                    keybind("Open Armory"),
                    keybind("Open Map (Toggle)"),
                    keybind("Open Map (Hold)"),
                    keybind("Show Scoreboard (Hold)"),
                    keybind("Toggle Cursor"),

                    section("Voting", height = 70),
                    keybind("Vote Option 1"),
                    keybind("Vote Option 2"),
                    keybind("Vote Option 3"),
                    keybind("Vote Option 4"),

                    section("Observer", height = 70),
                    keybind("Toggle Free Camera"),
                    keybind("Toggle Sight Lines"),
                    keybind("All Outlines"),
                    keybind("Friendly Outlines"),
                    keybind("Enemy Outlines"),
                    keybind("No Outlines"),
                    keybind("Toggle Player Loadout Visibility"),
                    keybind("Follow Projectiles"),
                    keybind("Cycle Preset Free Camera Locations"),
                    keybind("Hold to Observe Player in Free Camera"),
                    keybind("Follow Next Observer"),
                    keybind("Follow Previous Observer"),
                    keybind("Observe Player 1"),
                    keybind("Observe Player 2"),
                    keybind("Observe Player 3"),
                    keybind("Observe Player 4"),
                    keybind("Observe Player 5"),
                    keybind("Observe Player 6"),
                    keybind("Observe Player 7"),
                    keybind("Observe Player 8"),
                    keybind("Observe Player 9"),
                    keybind("Observe Player 10"),
                    slider("Right Analog Sensitivity X"),
                    slider("Right Analog Sensitivity Y"),
                    slider("Right Analog Deadzone"),
                    slider("Left Analog Deadzone"),
                    dropdown("Gamepad Right Stick Response Curve"),
                    switch("Gamepad Invert Look Y")
                )),
            )
        ),
        SimpleCategory(
            name = "Crosshair",
            buttonX = 960,
            startY = 302,
            windowHeight = 667,
            elements = listOf(
                section("General", height = 64),
                dropdown("Crosshair Color"),
                switch("Outlines"),
                slider("Outline Opacity"),
                slider("Outline Thickness"),
                switch("Center Dot"),
                slider("Center Dot Opacity"),
                slider("Center Dot Thickness"),
                switch("Fade Crosshair With Firing Error"),
                switch("Show Spectated Player's Crosshair"),
                switch("Disable Crosshair"),

                section("Inner Lines", height = 96),
                switch("Show Inner Lines"),
                slider("Inner Line Opacity"),
                slider("Inner Line Length"),
                slider("Inner Line Thickness"),
                slider("Inner Line Offset"),
                switch("Inner Line Movement Error"), // prefixed for uniqueness
                switch("Inner Line Firing Error"),   // ^

                section("Outer Lines", height = 96),
                slider("Outer Line Opacity"),
                slider("Outer Line Length"),
                slider("Outer Line Thickness"),
                slider("Outer Line Offset"),
                switch("Outer Line Movement Error"), // prefixed for uniqueness
                switch("Outer Line Firing Error"),   // ^
            )
        ),
        TabbedCategory(
            name = "Video",
            buttonX = 1080,
            startY = 132,
            windowHeight = 797,
            tabs = listOf(
                Tab(name = "General", buttonX = 740, elementWidth = 988, elements = listOf(
                    dropdown("Display Mode").skip(),
                    dropdown("Resolution").skip(),
                    dropdown("Monitor").skip(),
                    switch("Aspect Ratio Method").skip(),

                    section("Advanced", height = 94),
                    switch("Limit FPS on Battery"),
                    field("Max FPS on Battery"),
                    switch("Limit FPS in Menus"),
                    field("Max FPS in Menus"),
                    switch("Limit FPS in Background"),
                    field("Max FPS in Background"),
                    switch("Limit FPS Always"),
                    field("Max FPS Always"),
                    dropdown("NVIDIA Reflex Low Latency")
                )),
                Tab(name = "Graphics Quality", buttonX = 960, elementWidth = 988, elements = listOf(
                    switch("Multithreaded Rendering"),
                    triple("Material Quality"),
                    triple("Texture Quality"),
                    triple("Detail Quality"),
                    triple("UI Quality"),
                    switch("Vignette"),
                    switch("VSync"),
                    dropdown("Anti-Aliasing"),
                    dropdown("Anisotropic Filtering"),
                    switch("Improve Clarity"),
                    switch("[BETA] Experimental Sharpening"),
                    switch("Bloom"),
                    switch("Distortion"),
                    switch("First Person Shadows")
                )),
                Tab(name = "Stats", buttonX = 1180, elementWidth = 988, elements = listOf(
                    quadruple("Client FPS"),
                    quadruple("Server Tick Rate"),
                    quadruple("Total Frame Time"),
                    quadruple("Idle Time"),
                    quadruple("CPU (Game) Time"),
                    quadruple("CPU (Render) Time"),
                    quadruple("CPU (RHI) Time"),
                    quadruple("Network Round Trip Time"),
                    quadruple("Packet Loss"),
                    quadruple("Game to Render Latency"),
                    quadruple("Game Latency"),
                    quadruple("Render Latency"),
                ))
            )
        )
    )
}

private fun section(name: String, height: Int? = null) =
    height?.let { SectionElement(name, height) } ?: SectionElement(name)

private fun dropdown(name: String) = DropdownOptionElement(name)
private fun slider(name: String) = SliderOptionElement(name)
private fun switch(name: String) = SwitchOptionElement(name)
private fun triple(name: String) = TripleOptionElement(name)
private fun quadruple(name: String) = QuadrupleOptionElement(name)
private fun keybind(name: String) = KeybindOptionElement(name)
private fun singleKeybind(name: String) = SingleKeybindOptionElement(name)
private fun field(name: String) = FieldOptionElement(name)