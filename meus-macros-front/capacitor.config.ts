import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'br.com.roselabs.meusmacros',
  appName: 'Meus Macros',
  webDir: 'dist/meus-macros-front/browser',
  server: {
    androidScheme: 'https'
  },
  plugins: {
    SplashScreen: {
      launchShowDuration: 2000,
      backgroundColor: '#10b981',
      showSpinner: false,
      splashFullScreen: true,
      splashImmersive: true
    },
    StatusBar: {
      style: 'light',
      backgroundColor: '#10b981',
      overlaysWebView: false
    },
    Keyboard: {
      resize: 'ionic'
    }
  },
  ios: {
    contentInset: 'automatic',
    scheme: 'App',
    allowsLinkPreview: false,
    scrollEnabled: true
  }
};

export default config;
