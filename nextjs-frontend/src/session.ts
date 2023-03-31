import { DefaultSession } from "next-auth";

interface MyUser {
    accessToken?: string | null;
}

export const isAuthenticated = async (session: MySession | null) =>
    session && Math.floor(Date.now()) < (session.user as any).expires_at * 1000
  
export interface MySession extends Omit<DefaultSession, "user"> {
    user?: MyUser;
    expires: string;
}