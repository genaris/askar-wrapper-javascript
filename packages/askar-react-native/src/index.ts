import { NativeAskar } from '@openwallet-foundation/askar-shared'

import type { NativeBindings } from './NativeBindings'
import { ReactNativeAskar } from './ReactNativeAskar'
import AskarTurboModule from './specs/NativeAskar'

// Reexport everything from shared
export * from '@openwallet-foundation/askar-shared'

if (!AskarTurboModule.install()) {
  throw Error('Unable to install the turboModule: askar')
}

// This can already check whether `_askar` exists on global
// biome-ignore lint/correctness/noInvalidUseBeforeDeclaration:
if (!_askar) {
  throw Error('_askar has not been exposed on global. Something went wrong while installing the turboModule')
}

declare let _askar: NativeBindings

NativeAskar.register(new ReactNativeAskar(_askar))
