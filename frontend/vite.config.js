import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { viteSourceLocator } from '@metagptx/vite-plugin-source-locator'

export default defineConfig({
  server: {
    port: 5173 // 👈 Force Vite to use port 5173
  },
  plugins: [
    viteSourceLocator({ prefix: 'mgx' }),
    react()
  ]
})
