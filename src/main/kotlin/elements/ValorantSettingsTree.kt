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
                Tab(name = "Interface", buttonX = 1235, elements = listOf()),
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
private fun keybind(name: String) = KeybindOptionElement(name)
private fun singleKeybind(name: String) = SingleKeybindOptionElement(name)