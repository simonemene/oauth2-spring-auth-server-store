import { AuthConfig } from "angular-oauth2-oidc";

export const authConfig: AuthConfig = 
{
   issuer: 'http://localhost:8081',
   redirectUri: 'http://localhost:4200/welcome',
   clientId: 'store-security-pkce',
   responseType: 'code',
   scope:'openid profile email',
   requireHttps: false,
   showDebugInformation: true
}