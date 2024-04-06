package com.k0dm.atlacharacters.main

import com.k0dm.atlacharacters.core.Clear
import com.k0dm.atlacharacters.core.UiObservable
import com.k0dm.atlacharacters.core.UpdateUi
import com.k0dm.atlacharacters.core.UpdateUiObserver

interface Navigation {

    interface Navigate: UpdateUi<Screen>

    interface Observe: UpdateUiObserver<Screen>

    interface Mutable: Navigate, Observe, Clear

    class Base: Mutable, UiObservable.Base<Screen>(Screen.Empty)
}


