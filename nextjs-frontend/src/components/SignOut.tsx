import { signOut } from "next-auth/react";



export default function SignOut()
{
    return (
        <div>
            <button onClick={() => signOut()}>Sign out</button>
        </div>
    )
}